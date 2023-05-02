package org.mifos.connector.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.mifos.connector.common.interceptor.service.JsonWebSignatureService;
import org.mifos.connector.common.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.ContentCachingResponseWrapper;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.mifos.connector.common.util.Constant;

@Component
@ConditionalOnExpression("${security.jws.enable} and ${security.jws.response.enable}")
@Slf4j
public class JWSFilterStrategy extends GenericFilterBean {

    @Autowired
    WebSignatureInterceptor webSignatureInterceptor;

    @Autowired
    private JsonWebSignatureService jsonWebSignatureService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Started doFilter");

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(httpResponse);
        chain.doFilter(request, wrappedResponse);

        byte[] responseBytes = wrappedResponse.getContentAsByteArray();
        String responseBody = new String(responseBytes, httpResponse.getCharacterEncoding());

        String clientCorrelationId = httpResponse.getHeader(Constant.HEADER_CORRELATION_ID);
        try {
            String dataToBeHashed = webSignatureInterceptor.getDataToBeHashed(httpResponse, responseBody);
            String tenantName = webSignatureInterceptor.getTenantLocalStore().get(clientCorrelationId);
            String signature = jsonWebSignatureService.signForTenant(dataToBeHashed, tenantName);

            wrappedResponse.setHeader(Constant.HEADER_CORRELATION_ID, null);
            wrappedResponse.setHeader(Constant.HEADER_JWS, signature);
            log.info("ClientCorrelationId: {}", clientCorrelationId);
            log.info("Response str: {}", responseBody);
            log.info("Out data: {}", dataToBeHashed);
            log.info("Signature: {}", signature);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while creating signature(SERVER TO CLIENT) stacktrace: {}", e.getMessage());
        } finally {
            webSignatureInterceptor.getTenantLocalStore().remove(clientCorrelationId);
            wrappedResponse.copyBodyToResponse();
        }

        log.info("Ended doFilter");
    }

}

package org.mifos.connector.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.mifos.connector.common.interceptor.service.JsonWebSignatureService;
import org.mifos.connector.common.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.List;

@Component
@ConditionalOnExpression("${security.jws.enable} and ${security.jws.response.enable}")
@Slf4j
public class JWSFilterStrategy extends GenericFilterBean {

    @Value("#{'${jws.header.order}'.split(',')}")
    private List<String> headerOrder;

    @Autowired
    private JsonWebSignatureService jsonWebSignatureService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("Started doFilter");

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(httpResponse);
        chain.doFilter(request, wrappedResponse);

        byte[] responseBytes = wrappedResponse.getContentAsByteArray();
        String responseBody = new String(responseBytes, httpResponse.getCharacterEncoding());

        String tenant = httpResponse.getHeader(Constant.HEADER_PLATFORM_TENANT_ID);
        log.debug("Platform-TenantId: {}", tenant);
        try {
            log.debug("Fetching data to be hashed from doFilter");
            String dataToBeHashed = JWSUtil.getDataToBeHashed(httpResponse, responseBody, headerOrder);
            String signature = jsonWebSignatureService.signForTenant(dataToBeHashed, tenant);

            wrappedResponse.setHeader(Constant.HEADER_JWS, signature);
            log.debug("Response str: {}", responseBody);
            log.debug("Out data: {}", dataToBeHashed);
            log.debug("Signature: {}", signature);
        } catch (Exception e) {
            log.debug("{}", e.getMessage());
            log.error("Error while creating signature(SERVER TO CLIENT) stacktrace: {}", e.getMessage());
        } finally {
            wrappedResponse.copyBodyToResponse();
        }
        log.debug("Ended doFilter");
    }

}

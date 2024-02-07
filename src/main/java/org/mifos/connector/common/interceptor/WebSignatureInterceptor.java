package org.mifos.connector.common.interceptor;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.mifos.connector.common.channel.dto.PhErrorDTO;
import org.mifos.connector.common.exception.PaymentHubError;
import org.mifos.connector.common.interceptor.service.JsonWebSignatureService;
import org.mifos.connector.common.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
@Slf4j
@Getter
public class WebSignatureInterceptor implements HandlerInterceptor {

    @Autowired
    private JsonWebSignatureService jsonWebSignatureService;

    @Value("#{'${jws.header.order}'.split(',')}")
    private List<String> headerOrder;

    @Value("#{'${jws.exception-endpoints}'.split(',')}")
    protected List<String> exceptionEndpoints;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // return true means forward this request and false means don;t forward this to controller
        log.info("Request at interceptor");
        for (String endpoint: exceptionEndpoints) {
            if (request.getRequestURL().toString().contains(endpoint)) {
                log.info("This is exception endpoint, hence passing the request without JWS validation");
                return true;
            }
        }
        log.info("URL: {}", request.getRequestURL().toString());
        if (headerOrder.size() == 0) {
            throw new RuntimeException("Header is null");
        }
        PhErrorDTO errorDTO = null;

        String signature = request.getHeader(Constant.HEADER_JWS);
        log.debug("Signature inbound: {}", signature);
        String data = null;
        try {
            data = JWSUtil.parseBodyPayload(request);
        } catch (Exception e) {
            errorDTO = new PhErrorDTO.PhErrorDTOBuilder(PaymentHubError.JWSVerificationServerError)
                    .developerMessage("Make sure to pass valid payload and header along with signature").build();
            JWSUtil.writeErrorResponse(response, errorDTO);
            return false;
        }
        String dataToBeHashed = JWSUtil.getDataToBeHashed(request, data, headerOrder);
        log.debug("Data to be hashed: {}", dataToBeHashed);

        String tenant = request.getHeader(Constant.HEADER_PLATFORM_TENANT_ID);

        Boolean isValidSignature = null;
        try {
            isValidSignature = jsonWebSignatureService.verifyForTenant(dataToBeHashed, signature, tenant);
        } catch (Exception e) {
            errorDTO = new PhErrorDTO.PhErrorDTOBuilder(PaymentHubError.InvalidPublicKeyConfigured)
                    .developerMessage("Certificate" + jsonWebSignatureService.getTenantKeysProperties().getCertificate(tenant)).build();
            JWSUtil.writeErrorResponse(response, errorDTO);
            return false;
        }

        if (isValidSignature) {
            log.info("Signature is valid");
        } else {
            log.error("JWS Signature verification failed: {}", signature);
            errorDTO = new PhErrorDTO.PhErrorDTOBuilder(PaymentHubError.InvalidJsonWebSignature)
                    .addErrorParameter(Constant.HEADER_JWS, signature)
                    .developerMessage("Pass the valid header value for " + Constant.HEADER_JWS).build();
            JWSUtil.writeErrorResponse(response, errorDTO);
            response.setStatus(400);
        }
        response.setHeader(Constant.HEADER_PLATFORM_TENANT_ID, tenant);
        log.info("Request ended at interceptor");
        return isValidSignature;
    }
}

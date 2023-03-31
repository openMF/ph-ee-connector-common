package org.mifos.connector.common.interceptor;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.io.IOUtils;
import org.mifos.connector.common.channel.dto.PhErrorDTO;
import org.mifos.connector.common.exception.PaymentHubError;
import org.mifos.connector.common.interceptor.service.JsonWebSignatureService;
import org.mifos.connector.common.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.charset.Charset;

public class WebSignatureInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JsonWebSignatureService jsonWebSignatureService = new JsonWebSignatureService();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // return true means forward this request and false means don;t forward this to controller
        logger.info("Request at interceptor");

        String signature = request.getHeader(Constant.HEADER_JWS);
        String data = jwsDataForVerification(request);
        String tenantId = request.getHeader(Constant.HEADER_PLATFORM_TENANT_ID);
        String correlationId = request.getHeader(Constant.HEADER_CORRELATION_ID);

        String dataToBeHashed = getDataToBeHashed(correlationId, tenantId, data);


        logger.debug("Data to be hashed: {}", jsonWebSignatureService);

        boolean isValidSignature = jsonWebSignatureService.verify(dataToBeHashed, signature);

        if (isValidSignature) {
            logger.debug("Signature is valid");
        } else {
            logger.error("JWS Signature verification failed: {}", signature);
            PhErrorDTO errorDTO = new PhErrorDTO.PhErrorDTOBuilder(PaymentHubError.InvalidJsonWebSignature)
                    .addErrorParameter(Constant.HEADER_JWS, signature)
                    .developerMessage("Pass the valid header value for " + Constant.HEADER_JWS)
                    .build();

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(getObjectMapper().writeValueAsString(errorDTO));
        }

        return isValidSignature;
    }

    public String getDataToBeHashed(String correlationId, String tenantId, String payload) {
        return correlationId + Constant.REST_REQUEST_DATA_SEPARATOR +
                tenantId + Constant.REST_REQUEST_DATA_SEPARATOR +
                payload + Constant.REST_REQUEST_DATA_SEPARATOR;
    }

    public String jwsDataForVerification(HttpServletRequest request) throws IOException, ServletException {
        String data;
        String body = IOUtils.toString(request.getInputStream(), Charset.defaultCharset());
        if (body.isEmpty()) {
            data = parseFormData(request);
        } else {
            data = body;
        }

        return data;
    }

    public String parseFormData(HttpServletRequest httpServletRequest) throws ServletException, IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for (Part part: httpServletRequest.getParts()) {
            String partString = IOUtils.toString(part.getInputStream(), Charset.defaultCharset());
            stringBuilder.append(partString);
        }
        return stringBuilder.toString();
    }

    public ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setVisibilityChecker(
                objectMapper.getSerializationConfig()
                        .getDefaultVisibilityChecker()
                        .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                        .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
        );
        return objectMapper
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}

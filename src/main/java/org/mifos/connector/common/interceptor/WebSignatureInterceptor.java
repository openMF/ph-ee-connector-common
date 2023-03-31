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
import org.mifos.connector.common.exception.PaymentHubErrorCategory;
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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // return true means forward this request and false means don;t forward this to controller
        logger.info("Request at interceptor");
        PhErrorDTO errorDTO = null;

        String signature = request.getHeader(Constant.HEADER_JWS);
        String data = null;
        try {
            data = jwsDataForVerification(request);
        } catch (Exception e) {
            errorDTO = new PhErrorDTO.PhErrorDTOBuilder(PaymentHubError.JWSVerificationServerError)
                    .developerMessage("Make sure to pass valid payload and header along with signature").build();
            writeErrorResponse(response, errorDTO);
            return false;
        }

        String tenantId = request.getHeader(Constant.HEADER_PLATFORM_TENANT_ID);
        String correlationId = request.getHeader(Constant.HEADER_CORRELATION_ID);

        String dataToBeHashed = getDataToBeHashed(correlationId, tenantId, data);


        logger.debug("Data to be hashed: {}", jsonWebSignatureService);

        Boolean isValidSignature = null;
        try {
            isValidSignature = jsonWebSignatureService.verify(dataToBeHashed, signature);
        } catch (Exception e) {
            errorDTO = new PhErrorDTO.PhErrorDTOBuilder(PaymentHubError.InvalidPublicKeyConfigured).build();
            writeErrorResponse(response, errorDTO);
            return false;
        }

        if (isValidSignature) {
            logger.debug("Signature is valid");
        } else {
            logger.error("JWS Signature verification failed: {}", signature);
            errorDTO = new PhErrorDTO.PhErrorDTOBuilder(PaymentHubError.InvalidJsonWebSignature)
                    .addErrorParameter(Constant.HEADER_JWS, signature)
                    .developerMessage("Pass the valid header value for " + Constant.HEADER_JWS)
                    .build();
            writeErrorResponse(response, errorDTO);
        }
        return isValidSignature;
    }

    /**
     * Takes necessary information and formats it in a specific order, this is the format which is to be used while
     * verifying the JWS. Make sure while sending the request JWS is generated using this particular order.
     *
     * [X-CorrelationID]:[tenant-id]:[bodyform-data]
     *
     * @param correlationId unique ID for making sure the idempotency of the API
     * @param tenantId tenant name/ID
     * @param payload can be raw/json body or the form data
     * @return data which is to be verified in particular format
     */
    public String getDataToBeHashed(String correlationId, String tenantId, String payload) {
        return correlationId + Constant.REST_REQUEST_DATA_SEPARATOR +
                tenantId + Constant.REST_REQUEST_DATA_SEPARATOR +
                payload + Constant.REST_REQUEST_DATA_SEPARATOR;
    }

    /**
     * Writes the response to the output stream, used for returning the error response in case of JWS verification failure
     * @param response instance of [HttpServletResponse]
     * @param errorDTO error object containing valid PH-EE error
     * @see PhErrorDTO
     */
    public void writeErrorResponse(HttpServletResponse response, PhErrorDTO errorDTO) {
        if (errorDTO.getErrorCategory().equals(PaymentHubErrorCategory.System.name())) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        try {
            response.getWriter().write(getObjectMapper().writeValueAsString(errorDTO));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Created the data which is to be validated against the JWS passed
     * @param request request object passed to the controller
     * @return data for verification in form of string
     * @throws IOException throws as part of input stream
     * @throws ServletException inherited from functional chain
     */
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

    /**
     * Use to parse the string data from the multipart data passed in api
     * @param httpServletRequest request object passed to the controller
     * @return multipart data in form of string
     * @throws ServletException
     * @throws IOException
     */
    public String parseFormData(HttpServletRequest httpServletRequest) throws ServletException, IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for (Part part: httpServletRequest.getParts()) {
            String partString = IOUtils.toString(part.getInputStream(), Charset.defaultCharset());
            stringBuilder.append(partString);
        }
        return stringBuilder.toString();
    }

    /**
     * Need object mapper for serializing the error response
     * @return instance of [ObjectMapper]
     */
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

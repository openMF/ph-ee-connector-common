package org.mifos.connector.common.interceptor;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.mifos.connector.common.channel.dto.PhErrorDTO;
import org.mifos.connector.common.exception.PaymentHubError;
import org.mifos.connector.common.exception.PaymentHubErrorCategory;
import org.mifos.connector.common.interceptor.service.JsonWebSignatureService;
import org.mifos.connector.common.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
public class WebSignatureInterceptor implements HandlerInterceptor {

    @Autowired
    private JsonWebSignatureService jsonWebSignatureService;

    @Value("#{'${jws.header.order}'.split(',')}")
    private List<String> headers;

    private final HashMap<String, String> tenantLocalStore = new HashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // return true means forward this request and false means don;t forward this to controller
        log.debug("Request at interceptor");
        if (headers.size() == 0) {
            throw new RuntimeException("Header is null");
        }
        PhErrorDTO errorDTO = null;

        String signature = request.getHeader(Constant.HEADER_JWS);
        String data = null;
        try {
            data = parseBodyPayload(request);
        } catch (Exception e) {
            errorDTO = new PhErrorDTO.PhErrorDTOBuilder(PaymentHubError.JWSVerificationServerError)
                    .developerMessage("Make sure to pass valid payload and header along with signature").build();
            writeErrorResponse(response, errorDTO);
            return false;
        }

        String dataToBeHashed = getDataToBeHashed(request, data);
        log.debug("Data to be hashed: {}", jsonWebSignatureService);

        String tenant = request.getHeader(Constant.HEADER_PLATFORM_TENANT_ID);
        tenantLocalStore.put(signature, tenant);

        Boolean isValidSignature = null;
        try {
            isValidSignature = jsonWebSignatureService.verifyForTenant(dataToBeHashed, signature, tenant);
        } catch (Exception e) {
            errorDTO = new PhErrorDTO.PhErrorDTOBuilder(PaymentHubError.InvalidPublicKeyConfigured)
                    .developerMessage("Certificate" +
                            jsonWebSignatureService.getTenantKeysProperties().getCertificate(tenant)).build();
            writeErrorResponse(response, errorDTO);
            return false;
        }

        if (isValidSignature) {
            log.debug("Signature is valid");
        } else {
            log.error("JWS Signature verification failed: {}", signature);
            errorDTO = new PhErrorDTO.PhErrorDTOBuilder(PaymentHubError.InvalidJsonWebSignature)
                    .addErrorParameter(Constant.HEADER_JWS, signature)
                    .developerMessage("Pass the valid header value for " + Constant.HEADER_JWS)
                    .build();
            writeErrorResponse(response, errorDTO);
        }
        response.setHeader(Constant.HEADER_JWS, signature);
        return isValidSignature;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String bodyPayload = parseBodyPayload(request);
        String dataToBeHashed = getDataToBeHashed(request, bodyPayload);
        String tenantName = tenantLocalStore.get(response.getHeader(Constant.HEADER_JWS));
        String signature = jsonWebSignatureService.signForTenant(dataToBeHashed, tenantName);
        response.addHeader(Constant.HEADER_JWS, signature);
        log.debug("Out signature: {}", signature);
        //HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * Takes necessary information and formats it in a specific order, this is the format which is to be used while
     * verifying the JWS. The order of header is added as a configuration, refer to jws.header.order in application.yaml.
     *
     * @param request request object passed to the controller
     * @param payload can be raw/json body or the form data
     * @return data which is to be verified in particular format
     */
    public String getDataToBeHashed(HttpServletRequest request, String payload) {
        StringBuilder dataBuilder = new StringBuilder();
        for (String headerKey: headers) {
            String headerValue = request.getHeader(headerKey);
            if (headerValue != null) {
                dataBuilder.append(headerValue).append(Constant.REST_REQUEST_DATA_SEPARATOR);
            }
        }
        if (payload != null && !payload.isEmpty()) {
            dataBuilder.append(payload);
        } else {
            // remove the last added [Constant.REST_REQUEST_DATA_SEPARATOR]
            dataBuilder.deleteCharAt(dataBuilder.length()-1);
        }
        return dataBuilder.toString();
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
            response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
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
    public String parseBodyPayload(HttpServletRequest request) throws IOException, ServletException {
        String data = null;
        String body = IOUtils.toString(request.getInputStream(), Charset.defaultCharset());
        if (body.isEmpty() && !request.getMethod().equals(HttpMethod.GET.name())) {
            // parse form data only if body is empty and REQUEST TYPE is not GET
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

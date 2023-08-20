package org.mifos.connector.common.interceptor;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.mifos.connector.common.channel.dto.PhErrorDTO;
import org.mifos.connector.common.exception.PaymentHubErrorCategory;
import org.mifos.connector.common.util.Constant;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collection;

@Slf4j
public final class JWSUtil {

    private JWSUtil() {}

    /**
     * Takes necessary information and formats it in a specific order, this is the format which is to be used while
     * verifying the JWS. The order of header is added as a configuration, refer to jws.header.order in
     * application-jws.yaml.
     *
     * @param request
     *            request object passed to the controller
     * @param payload
     *            can be raw/json body or the form data
     * @param headerOrder
     *            list of headers in specific order from configuration
     * @return data which is to be verified in particular format
     */
    public static String getDataToBeHashed(HttpServletRequest request, String payload, List<String> headerOrder) {
        HashMap<String, Object> headers = new HashMap<>();
        Enumeration<String> headersEnumeration = request.getHeaderNames();
        while (headersEnumeration.hasMoreElements()) {
            String headerKey = headersEnumeration.nextElement().toUpperCase();
            headers.put(headerKey, request.getHeader(headerKey));
        }
        log.debug("Headers: {}", headers);
        return getDataToBeHashed(headers, payload, headerOrder);
    }

    /**
     * Takes necessary information and formats it in a specific order, this is the format which is to be used while
     * verifying the JWS. The order of header is added as a configuration, refer to jws.header.order in
     * application-jws.yaml.
     *
     * @param response
     *            response object
     * @param payload
     *            can be raw/json body or the form data
     * @param headerOrder
     *            list of headers in specific order from configuration
     * @return data which is to be verified in particular format
     */
    public static String getDataToBeHashed(HttpServletResponse response, String payload, List<String> headerOrder) {
        HashMap<String, Object> headers = new HashMap<>();
        for (String headerKey : response.getHeaderNames()) {
            headers.put(headerKey, response.getHeader(headerKey));
        }
        return getDataToBeHashed(headers, payload, headerOrder);
    }

    /**
     * Takes necessary information and formats it in a specific order, this is the format which is to be used while
     * verifying the JWS. The order of header is added as a configuration, refer to jws.header.order in
     * application-jws.yaml.
     *
     * @param header
     *            hashmap of header, with all the header in capital case
     * @param payload
     *            can be raw/json body or the form data
     * @param headerOrder
     *            list of headers in specific order from configuration
     * @return data which is to be verified in particular format
     */
    public static String getDataToBeHashed(Map<String, Object> header, String payload, List<String> headerOrder) {
        StringBuilder dataBuilder = new StringBuilder();
        for (String headerKey : headerOrder) {
            String headerValue = (String) header.get(headerKey.toUpperCase());
            log.debug("Header: {} : {}", headerKey, headerValue);
            if (headerValue != null) {
                dataBuilder.append(headerValue).append(Constant.REST_REQUEST_DATA_SEPARATOR);
            }
        }
        if (payload != null && !payload.isEmpty()) {
            dataBuilder.append(payload);
        } else {
            // remove the last added [Constant.REST_REQUEST_DATA_SEPARATOR]
            dataBuilder.deleteCharAt(dataBuilder.length() - 1);
        }
        return dataBuilder.toString();
    }

    /**
     * Writes the response to the output stream, used for returning the error response in case of JWS verification
     * failure
     *
     * @param response
     *            instance of [HttpServletResponse]
     * @param errorDTO
     *            error object containing valid PH-EE error
     * @see PhErrorDTO
     */
    public static void writeErrorResponse(HttpServletResponse response, PhErrorDTO errorDTO) {
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
     *
     * @param request
     *            request object passed to the controller
     * @return data for verification in form of string
     * @throws IOException
     *             throws as part of input stream
     * @throws ServletException
     *             inherited from functional chain
     */
    public static String parseBodyPayload(HttpServletRequest request) throws IOException, ServletException {
        String data = null;
        String body = IOUtils.toString(request.getInputStream(), Charset.defaultCharset());
        if (body.isEmpty() && !request.getMethod().equals(HttpMethod.GET.name())) {
            // parse form data only if body is empty and REQUEST TYPE is not GET
            data = parseFormData(request);
        } else {
            data = body;
        }
        log.debug("Parsed data: {}", data);
        return data;
    }

    /**
     * Use to parse the string data from the multipart data passed in api
     *
     * @param httpServletRequest
     *            request object passed to the controller
     * @return multipart data in form of string
     * @throws ServletException
     * @throws IOException
     */
    public static String parseFormData(HttpServletRequest httpServletRequest) throws ServletException, IOException {
        log.debug("Parsing form data");
        StringBuilder stringBuilder = new StringBuilder();
        Collection<Part> parts;
        try {
            parts = httpServletRequest.getParts();
            assert parts != null;
            assert !parts.isEmpty();
        } catch (Exception e) {
            log.warn("Empty payload in multipart form: {}", e.getLocalizedMessage());
            return "";
        }
        log.debug("HttpServletRequest: {}", parts);
        for (Part part: parts) {
            log.debug("Part loop");
            String partString = IOUtils.toString(part.getInputStream(), Charset.defaultCharset());
            stringBuilder.append(partString);
        }
        return stringBuilder.toString();
    }

    /**
     * Need object mapper for serializing the error response
     *
     * @return instance of [ObjectMapper]
     */
    public static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setVisibilityChecker(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY).withGetterVisibility(JsonAutoDetect.Visibility.NONE));
        return objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

}

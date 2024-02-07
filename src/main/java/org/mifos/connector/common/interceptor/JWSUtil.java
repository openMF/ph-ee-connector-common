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
import org.mifos.connector.common.exception.PaymentHubErrorCategory;
import org.mifos.connector.common.util.Constant;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

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
            response.setHeader(CONTENT_TYPE, "application/json");
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
     */
    public static String parseBodyPayload(HttpServletRequest request) throws IOException {
        log.debug("Content-type: {}", request.getHeader(CONTENT_TYPE));
        String data = null;
        String body = IOUtils.toString(request.getInputStream(), Charset.defaultCharset());
        if (isMultipartRequest(request)
                && !request.getMethod().equals(HttpMethod.GET.name())) {
            // parse form data only if body is empty and REQUEST TYPE is not GET
            data = parseFormData(request);
        } else {
            data = body;
        }
        log.debug("Parsed data: {}", data);
        return data;
    }

    // parses the boundary value form the content type
    // works if content type is of below kind
    // 'multipart/form-data; boundary=--------------------------188270859567880981670528'
    public static String parseBoundaryValueFromContentTypeValue(String contentType) {
        String[] contentTypeArray = contentType.split("boundary=");
        if (contentTypeArray.length < 2) {
            return "";
        }
        String boundary = contentTypeArray[1].strip();
        int pos = boundary.indexOf(';');
        return pos == -1 ? boundary : boundary.substring(0,pos).strip();
    }

    /**
     * Use to parse the string data from the multipart data passed in api
     *
     * @param request
     *            request object passed to the controller
     * @return multipart data in form of string
     * @throws IOException
     */
    public static String parseFormData(HttpServletRequest request) throws IOException {
        log.debug("Parsing form data");
        String contentTypeHeaderValue = request.getHeader(CONTENT_TYPE);
        String boundary = parseBoundaryValueFromContentTypeValue(contentTypeHeaderValue);
        return parseFormData(request.getInputStream(), boundary);
    }

    /**
     * Use to parse the multipart/form-data  from the input stream
     *
     * @param inputStream
     * @param boundary the boundary can be parsed from CONTENT-TYPE header in
     *                 API request header
     * @return parsed data in the form of String
     * @throws IOException
     */
    public static String parseFormData(InputStream inputStream, String boundary) throws IOException {
        log.debug("inside MultipartParser");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder partContent = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains(boundary)) {
                // Read and process the part headers
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    // Process part headers if needed
                }

                // Read and process the part content
                while ((line = reader.readLine()) != null && !line.equals(boundary)
                        && !line.contains(boundary) && !line.equals("\n") && !line.isEmpty()) {
                    partContent.append(line);
                    partContent.append("\n");
                }
            }
        }
        return partContent.toString().trim();
    }

    // returns true if the request is of multipart type
    public static boolean isMultipartRequest(HttpServletRequest request) {
        return request.getHeader(CONTENT_TYPE).contains("multipart/form-data");
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

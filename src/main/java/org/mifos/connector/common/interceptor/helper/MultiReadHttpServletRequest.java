package org.mifos.connector.common.interceptor.helper;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.Part;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import org.springframework.util.StreamUtils;

public class MultiReadHttpServletRequest extends HttpServletRequestWrapper {

    private byte[] body;

    /**
     * Creates a ServletRequest adaptor wrapping the given request object.
     *
     * @param request
     * @throws IllegalArgumentException
     *             if the request is null
     */
    public MultiReadHttpServletRequest(HttpServletRequest request) {
        super(request);
        try {
            body = StreamUtils.copyToByteArray(request.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ByteArrayServletInputStream(body);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.body);
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        return ((HttpServletRequest) getRequest()).getParts();
    }

}

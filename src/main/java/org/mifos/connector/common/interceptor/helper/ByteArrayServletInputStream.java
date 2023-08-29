package org.mifos.connector.common.interceptor.helper;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ByteArrayServletInputStream extends ServletInputStream {

    private final ByteArrayInputStream inputStream;

    public ByteArrayServletInputStream(byte[] bytes) {
        inputStream = new ByteArrayInputStream(bytes);
    }

    @Override
    public int read() throws IOException {
        return inputStream.read();
    }

    @Override
    public boolean isFinished() {
        return inputStream.available() == 0;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
    }

}

package org.mifos.connector.common.interceptor;

import org.mifos.connector.common.util.Constant;
import org.mifos.connector.common.util.SecurityUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class HelloController {

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @PostMapping("/")
    public String postIndex() {
        return "Greetings from Spring Boot!";
    }

    @PostMapping ("signature")
    public String signature(@RequestParam("privateKey") String privateKey, HttpServletRequest request) throws ServletException, IOException {
        WebSignatureInterceptor interceptor = new WebSignatureInterceptor();

        String data = interceptor.jwsDataForVerification(request);
        String tenantId = request.getHeader(Constant.HEADER_PLATFORM_TENANT_ID);
        String correlationId = request.getHeader(Constant.HEADER_CORRELATION_ID);

        String dataToBeHashed = interceptor.getDataToBeHashed(correlationId, tenantId, data);


        return SecurityUtil.hash(dataToBeHashed);
    }

    @GetMapping ("signature")
    public String signatureGet(@RequestParam("privateKey") String privateKey, HttpServletRequest request) throws ServletException, IOException {
        WebSignatureInterceptor interceptor = new WebSignatureInterceptor();

        String data = interceptor.jwsDataForVerification(request);
        String tenantId = request.getHeader(Constant.HEADER_PLATFORM_TENANT_ID);
        String correlationId = request.getHeader(Constant.HEADER_CORRELATION_ID);

        String dataToBeHashed = interceptor.getDataToBeHashed(correlationId, tenantId, data);


        return SecurityUtil.hash(dataToBeHashed);
    }

}

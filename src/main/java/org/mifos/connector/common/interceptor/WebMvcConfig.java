package org.mifos.connector.common.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Component
@ConditionalOnExpression("${jws.enable}")
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    private WebSignatureInterceptor webSignatureInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webSignatureInterceptor);
        super.addInterceptors(registry);
    }

}

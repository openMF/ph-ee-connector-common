package org.mifos.connector.common.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Component("JWSMvcConfig")
@ConditionalOnExpression("${security.jws.enable:false}")
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    private WebSignatureInterceptor webSignatureInterceptor;

    @Autowired(required = false)
    private JWSFilterStrategy jwsFilterStrategy;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webSignatureInterceptor);
        super.addInterceptors(registry);
    }

    @Bean
    @ConditionalOnProperty(prefix = "security.jws.response", name = "enable", havingValue = "true")
    public FilterRegistrationBean jwsFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(jwsFilterStrategy);
        registration.addUrlPatterns("/*");
        registration.setOrder(Integer.MIN_VALUE+2);
        return registration;
    }

}

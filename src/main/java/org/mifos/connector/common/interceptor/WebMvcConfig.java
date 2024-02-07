package org.mifos.connector.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Component("JWSMvcConfig")
@Slf4j
@ConditionalOnExpression("${security.jws.enable:false}")
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    private WebSignatureInterceptor webSignatureInterceptor;

    @Autowired(required = false)
    private JWSFilterStrategy jwsFilterStrategy;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.debug("Added interceptor");
        registry.addInterceptor(webSignatureInterceptor);
        super.addInterceptors(registry);
    }

    @Bean
    public FilterRegistrationBean cachingFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new MultiReadFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }


    @Bean
    @ConditionalOnProperty(prefix = "security.jws.response", name = "enable", havingValue = "true")
    public FilterRegistrationBean jwsFilter() {
        log.debug("Registered filter");
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(jwsFilterStrategy);
        registration.addUrlPatterns("/*");
        registration.setOrder(Integer.MIN_VALUE + 2);
        return registration;
    }

}

package org.mifos.connector.common.interceptor.config;

import org.mifos.connector.common.interceptor.WebMvcConfig;
import org.mifos.connector.common.interceptor.WebSignatureInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ComponentScan(basePackageClasses = WebSignatureInterceptor.class)
@Configuration
@PropertySource(value = "classpath:jws.yaml", factory = YamlPropertySourceFactory.class)
public class WebSignatureConfiguration {}

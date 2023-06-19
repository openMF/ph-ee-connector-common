package org.mifos.connector.common.interceptor.config;

import org.mifos.connector.common.camel.JWSRoute;
import org.mifos.connector.common.interceptor.WebSignatureInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ComponentScan(basePackageClasses = { WebSignatureInterceptor.class, JWSRoute.class })
@Configuration
@PropertySource(value = { "classpath:application-jws.yaml" }, factory = YamlPropertySourceFactory.class)
public class WebSignatureConfiguration {}

package org.mifos.connector.common.interceptor.annotation;

import org.mifos.connector.common.interceptor.config.WebSignatureConfiguration;
import org.mifos.connector.common.interceptor.properties.TenantKeysProperties;
import org.springframework.context.annotation.Import;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({ WebSignatureConfiguration.class, TenantKeysProperties.class})
public @interface EnableJsonWebSignature {}

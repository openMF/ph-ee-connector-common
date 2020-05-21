package org.mifos.mojaloop;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TestConfig {

    @Bean
    public RestTemplate template(RestTemplateBuilder builder) {
        return builder.build();
    }
}

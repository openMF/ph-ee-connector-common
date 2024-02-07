package org.mifos.connector.common.camel;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnExpression("${rest.authorization.enabled:false}")
@ConfigurationProperties(prefix = "rest.authorization")
public class AuthProperties {

    private List<EndpointSetting> settings = new ArrayList<>();

    public AuthProperties() {}

    public List<EndpointSetting> getSettings() {
        return settings;
    }
}

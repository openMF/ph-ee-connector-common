package org.mifos.mojaloop;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mojaloop")
public class CallbackMappingProperties {

    private List<CallbackMapping> callbackMappings = new ArrayList<>();

    public CallbackMappingProperties() {}

    public List<CallbackMapping> getCallbackMappings() {
        return callbackMappings;
    }

    public void setCallbackMappings(List<CallbackMapping> callbackMappings) {
        this.callbackMappings = callbackMappings;
    }
}

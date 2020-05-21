package org.mifos.mojaloop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "mojaloop")
public class CallbackMappingProperties {

    private List<CallbackMapping> callbackMappings = new ArrayList<>();

    public CallbackMappingProperties() {
    }

    public List<CallbackMapping> getCallbackMappings() {
        return callbackMappings;
    }

    public void setCallbackMappings(List<CallbackMapping> callbackMappings) {
        this.callbackMappings = callbackMappings;
    }
}

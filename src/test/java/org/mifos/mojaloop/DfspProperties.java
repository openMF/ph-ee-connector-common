package org.mifos.mojaloop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "mojaloop")
public class DfspProperties {

    private List<Dfsp> dfsps = new ArrayList<>();

    public DfspProperties() {
    }

    public List<Dfsp> getDfsps() {
        return dfsps;
    }

    public void setDfsps(List<Dfsp> dfsps) {
        this.dfsps = dfsps;
    }
}

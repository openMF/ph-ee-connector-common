package org.mifos.connector.common.interceptor.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jws")
@Getter
@Setter
public class TenantKeysProperties {

    private List<TenantKeys> tenantKeys = new ArrayList<>();

    public TenantKeys getTenantKeys(String tenantName) {
        Optional<TenantKeys> tenantKeys = getTenantKeys().stream().filter(t -> t.getName().equalsIgnoreCase(tenantName)).findFirst();
        return tenantKeys.orElseGet(() -> getTenantKeys().stream().filter(t -> t.getName().equalsIgnoreCase("default")).findFirst().get());
    }

    public String getPrivateKey(String tenantName) {
        return getTenantKeys(tenantName).getPrivateKey();
    }

    public String getCertificate(String tenantName) {
        return getTenantKeys(tenantName).getCertificate();
    }

}

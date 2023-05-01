package org.mifos.connector.common.interceptor.properties;

import lombok.Getter;
import lombok.Setter;
import org.mifos.connector.common.exception.PaymentHubError;
import org.mifos.connector.common.exception.PaymentHubException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@ConfigurationProperties(prefix = "jws")
@Getter
@Setter
public class TenantKeysProperties {

    private List<TenantKeys> tenantKeys = new ArrayList<>();

    public TenantKeys getTenantKeys(String tenantName) {
        Optional<TenantKeys> tenantKeys = getTenantKeys().stream()
                .filter(t -> t.getName().equalsIgnoreCase(tenantName))
                .findFirst();
        return tenantKeys.orElseGet(() -> getTenantKeys().stream()
                .filter(t -> t.getName().equalsIgnoreCase("default"))
                .findFirst().get());
    }

    public String getPrivateKey(String tenantName) {
        return getTenantKeys(tenantName).getPrivateKey();
    }

    public String getCertificate(String tenantName) {
        return getTenantKeys(tenantName).getCertificate();
    }

}

package org.mifos.connector.common.interceptor.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TenantKeys {

    private String name;
    private String privateKey;
    private String certificate;
}

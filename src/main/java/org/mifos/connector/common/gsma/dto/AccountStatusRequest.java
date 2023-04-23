package org.mifos.connector.common.gsma.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountStatusRequest {
    public String identifierType;
    public String identifier;

}

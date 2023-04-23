package org.mifos.connector.common.gsma.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AccountBalanceResponseDTO {
    private String currentBalance;
    private String availableBalance;
    private String reservedBalance;
    private String unclearedBalance;
    private String currency;
    private String accountStatus;
}

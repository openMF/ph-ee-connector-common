package org.mifos.connector.common.gsma.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;

@Getter
@Setter
@ToString
public class AuthorizationCodeDTO {

    private String authorisationCode;
    private String codeState;
    private String codeLifetime;
    private String requestDate;
    private String amount;
    private String currency;
    private String amountType;
    private String holdFundsIndicator;

    private RedemptionChannel[] redemptionChannels;
    private RedemptionTransactionType[] redemptionTransactionTypes;
    private GsmaParty[] redemptionAccountIdentifiers;
    private GsmaParty[] metadata;
}

package org.mifos.connector.common.vouchers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RedeemVoucherRequestDTO {

    private String requestId;
    private String agentId;
    private String voucherSerialNumber;
    private String voucherSecretNumber;
}

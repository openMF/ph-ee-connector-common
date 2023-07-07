package org.mifos.connector.common.vouchers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RedeemVoucherResponseDTO {
    private String status;
    private String message;
    private String serialNumber;
    private String value;
    private String timestamp;
    private String transactionId;
}

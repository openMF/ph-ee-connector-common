package org.mifos.connector.common.vouchers.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuccessfulVouchers {

    private String instructionID;
    private String currency;
    private BigDecimal amount;
    private String narration;
    private String voucherNumber;
    private String serialNumber;
}

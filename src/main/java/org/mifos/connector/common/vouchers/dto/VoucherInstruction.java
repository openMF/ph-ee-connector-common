package org.mifos.connector.common.vouchers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoucherInstruction {
    private String instructionID ;
    private String groupCode;
    private String currency ;
    private BigDecimal amount;
    private String payeeFunctionalID ;
    private String narration;
    private String voucherNumber;
    private String serialNumber;
    private String status;
}

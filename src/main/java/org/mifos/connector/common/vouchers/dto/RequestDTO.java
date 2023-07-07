package org.mifos.connector.common.vouchers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {
    public String requestID ;
    public String batchID;
    public ArrayList<VoucherInstruction> voucherInstructions;
}

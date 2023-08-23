package org.mifos.connector.common.vouchers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CallbackRequestDTO  {
    private String requestID;
    private String batchID;
    private List<SuccessfulVouchers> voucherInstructions;

}

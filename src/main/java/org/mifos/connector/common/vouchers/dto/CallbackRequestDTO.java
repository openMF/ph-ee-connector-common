package org.mifos.connector.common.vouchers.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CallbackRequestDTO {

    private String requestID;
    private String batchID;
    private List<SuccessfulVouchers> voucherInstructions;

}

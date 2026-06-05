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
public class VoucherLifecycleCallbackResponseDTO {

    private String requestId;
    private String registerRequestId;
    private Integer numberFailedCases;
    private List<FailedCaseDTO> failedCases;
}

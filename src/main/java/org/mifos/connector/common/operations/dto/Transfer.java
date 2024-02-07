package org.mifos.connector.common.operations.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mifos.connector.common.operations.type.TransferStatus;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Transfer {

    private Long workflowInstanceKey;
    private String transactionId;
    private Date startedAt;
    private Date completedAt;
    private TransferStatus status;
    private String statusDetail;
    private String payeeDfspId;
    private String payeePartyId;
    private String payeePartyIdType;
    private BigDecimal payeeFee;
    private String payeeFeeCurrency;
    private String payeeQuoteCode;
    private String payerDfspId;
    private String payerPartyId;
    private String payerPartyIdType;
    private BigDecimal payerFee;
    private String payerFeeCurrency;
    private String payerQuoteCode;
    private BigDecimal amount;
    private String currency;
    private String direction;

    private String errorInformation;

    private String batchId;

    private String clientCorrelationId;
}

package org.mifos.connector.common.gsma.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bill {

    private String currency;
    private String amountDue;
    private String dueDate;
    private String billReference;
    private String minimumAmountDue;
}

package org.mifos.connector.common.ams.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoanRepaymentDTO {
    @JsonProperty("transactionDate")
    public String transactionDate;
    @JsonProperty("paymentTypeId")
    public String paymentTypeId;
    @JsonProperty("transactionAmount")
    public String transactionAmount;
    @JsonProperty("locale")
    public String locale;
    @JsonProperty("dateFormat")
    public String dateFormat;
}
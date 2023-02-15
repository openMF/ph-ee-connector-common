package org.mifos.connector.common.ams.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
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
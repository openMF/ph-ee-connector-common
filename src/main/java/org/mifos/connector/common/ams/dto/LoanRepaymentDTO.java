package org.mifos.connector.common.ams.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    @Override
    public String toString() {
        return "LoanRepaymentDTO{" + "transactionDate='" + transactionDate + '\'' + ", paymentTypeId='" + paymentTypeId + '\''
                + ", transactionAmount='" + transactionAmount + '\'' + ", locale='" + locale + '\'' + ", dateFormat='" + dateFormat + '\''
                + '}';
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(String paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public LoanRepaymentDTO() {}

    public LoanRepaymentDTO(String transactionDate, String paymentTypeId, String transactionAmount, String locale, String dateFormat) {
        this.transactionDate = transactionDate;
        this.paymentTypeId = paymentTypeId;
        this.transactionAmount = transactionAmount;
        this.locale = locale;
        this.dateFormat = dateFormat;
    }
}

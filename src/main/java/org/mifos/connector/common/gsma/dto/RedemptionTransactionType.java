package org.mifos.connector.common.gsma.dto;

public class RedemptionTransactionType {

    private String transactionType;
    private String transactionSubtype;

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionSubtype() {
        return transactionSubtype;
    }

    public void setTransactionSubtype(String transactionSubtype) {
        this.transactionSubtype = transactionSubtype;
    }
}

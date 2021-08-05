package org.mifos.connector.common.gsma.dto;

public class Fee {
    private String feeType;
    private String feeAmount;
    private String feeCurrency;

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getFeeCurrency() {
        return feeCurrency;
    }

    public void setFeeCurrency(String feeCurrency) {
        this.feeCurrency = feeCurrency;
    }

    @Override
    public String toString() {
        return "Fee{" +
                "feeType='" + feeType + '\'' +
                ", feeAmount='" + feeAmount + '\'' +
                ", feeCurrency='" + feeCurrency + '\'' +
                '}';
    }
}

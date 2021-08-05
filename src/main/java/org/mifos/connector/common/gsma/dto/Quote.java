package org.mifos.connector.common.gsma.dto;

public class Quote {

    private String quoteId;
    private String quoteExpiryTime;
    private String receivingServiceProvider;
    private String sendingAmount;
    private String sendingCurrency;
    private String receivingAmount;
    private String receivingCurrency;
    private String fxRate;
    private String deliveryMethod;
    private Fee[] fees;

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public String getQuoteExpiryTime() {
        return quoteExpiryTime;
    }

    public void setQuoteExpiryTime(String quoteExpiryTime) {
        this.quoteExpiryTime = quoteExpiryTime;
    }

    public String getReceivingServiceProvider() {
        return receivingServiceProvider;
    }

    public void setReceivingServiceProvider(String receivingServiceProvider) {
        this.receivingServiceProvider = receivingServiceProvider;
    }

    public String getSendingAmount() {
        return sendingAmount;
    }

    public void setSendingAmount(String sendingAmount) {
        this.sendingAmount = sendingAmount;
    }

    public String getSendingCurrency() {
        return sendingCurrency;
    }

    public void setSendingCurrency(String sendingCurrency) {
        this.sendingCurrency = sendingCurrency;
    }

    public String getReceivingAmount() {
        return receivingAmount;
    }

    public void setReceivingAmount(String receivingAmount) {
        this.receivingAmount = receivingAmount;
    }

    public String getReceivingCurrency() {
        return receivingCurrency;
    }

    public void setReceivingCurrency(String receivingCurrency) {
        this.receivingCurrency = receivingCurrency;
    }

    public String getFxRate() {
        return fxRate;
    }

    public void setFxRate(String fxRate) {
        this.fxRate = fxRate;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public Fee[] getFees() {
        return fees;
    }

    public void setFees(Fee[] fees) {
        this.fees = fees;
    }
}

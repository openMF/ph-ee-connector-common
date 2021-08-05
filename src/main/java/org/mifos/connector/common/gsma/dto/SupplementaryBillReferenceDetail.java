package org.mifos.connector.common.gsma.dto;

public class SupplementaryBillReferenceDetail {

    private String paymentReferenceType;
    private String paymentReferenceValue;

    public String getPaymentReferenceType() {
        return paymentReferenceType;
    }

    public void setPaymentReferenceType(String paymentReferenceType) {
        this.paymentReferenceType = paymentReferenceType;
    }

    public String getPaymentReferenceValue() {
        return paymentReferenceValue;
    }

    public void setPaymentReferenceValue(String paymentReferenceValue) {
        this.paymentReferenceValue = paymentReferenceValue;
    }
}

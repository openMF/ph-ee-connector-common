package org.mifos.connector.common.identityaccountmapper.dto;

public class FailedCaseDTO {
    private String payeeIdentity ;
    private String paymentModality;
    private String failureReason;

    public FailedCaseDTO(String payeeIdentity, String paymentModality, String failureReason) {
        this.payeeIdentity = payeeIdentity;
        this.paymentModality = paymentModality;
        this.failureReason = failureReason;
    }

    public String getPayeeIdentity() {
        return payeeIdentity;
    }

    public void setPayeeIdentity(String payeeIdentity) {
        this.payeeIdentity = payeeIdentity;
    }

    public String getPaymentModality() {
        return paymentModality;
    }

    public void setPaymentModality(String paymentModality) {
        this.paymentModality = paymentModality;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }
}

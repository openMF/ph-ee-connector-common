package org.mifos.connector.common.gsma.dto;

public class BillPaymentDTO {

    private String currency;
    private String amountPaid;
    private String paidAmount;
    private String serviceProviderPaymentReference;
    private String billPaymentStatus;
    private String requestingOrganisation;
    private String requestingOrganisationTransactionReference;
    private String customerReference;
    private String paymentType;
    private String serviceProviderComment;
    private String serviceProviderNotification;
    private SupplementaryBillReferenceDetail[] supplementaryBillReferenceDetails;
    private GsmaParty[] metadata;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getServiceProviderPaymentReference() {
        return serviceProviderPaymentReference;
    }

    public void setServiceProviderPaymentReference(String serviceProviderPaymentReference) {
        this.serviceProviderPaymentReference = serviceProviderPaymentReference;
    }

    public String getBillPaymentStatus() {
        return billPaymentStatus;
    }

    public void setBillPaymentStatus(String billPaymentStatus) {
        this.billPaymentStatus = billPaymentStatus;
    }

    public String getRequestingOrganisation() {
        return requestingOrganisation;
    }

    public void setRequestingOrganisation(String requestingOrganisation) {
        this.requestingOrganisation = requestingOrganisation;
    }

    public String getRequestingOrganisationTransactionReference() {
        return requestingOrganisationTransactionReference;
    }

    public void setRequestingOrganisationTransactionReference(String requestingOrganisationTransactionReference) {
        this.requestingOrganisationTransactionReference = requestingOrganisationTransactionReference;
    }

    public String getCustomerReference() {
        return customerReference;
    }

    public void setCustomerReference(String customerReference) {
        this.customerReference = customerReference;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getServiceProviderComment() {
        return serviceProviderComment;
    }

    public void setServiceProviderComment(String serviceProviderComment) {
        this.serviceProviderComment = serviceProviderComment;
    }

    public String getServiceProviderNotification() {
        return serviceProviderNotification;
    }

    public void setServiceProviderNotification(String serviceProviderNotification) {
        this.serviceProviderNotification = serviceProviderNotification;
    }

    public SupplementaryBillReferenceDetail[] getSupplementaryBillReferenceDetails() {
        return supplementaryBillReferenceDetails;
    }

    public void setSupplementaryBillReferenceDetails(SupplementaryBillReferenceDetail[] supplementaryBillReferenceDetails) {
        this.supplementaryBillReferenceDetails = supplementaryBillReferenceDetails;
    }

    public GsmaParty[] getMetadata() {
        return metadata;
    }

    public void setMetadata(GsmaParty[] metadata) {
        this.metadata = metadata;
    }
}

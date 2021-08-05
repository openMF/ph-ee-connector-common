package org.mifos.connector.common.gsma.dto;

public class QuotesDTO {

    private String requestDate;
    private GsmaParty[] debitParty;
    private GsmaParty[] creditParty;
    private Kyc senderKyc;
    private Kyc recipientKyc;
    private String requestAmount;
    private String requestCurrency;
    private String type;
    private String subType;
    private String chosenDeliveryMethod;
    private Quote[] quotes;
    private String senderBlockingReason;
    private String recipientBlockingReason;
    private GsmaParty[] metadata;
    private String quotationReference;
    private String quotationStatus;
    private String creationDate;
    private String modificationDate;
    private String dateCreated;
    private String dateModified;
    private String availableDeliveryMethod;

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public GsmaParty[] getDebitParty() {
        return debitParty;
    }

    public void setDebitParty(GsmaParty[] debitParty) {
        this.debitParty = debitParty;
    }

    public GsmaParty[] getCreditParty() {
        return creditParty;
    }

    public void setCreditParty(GsmaParty[] creditParty) {
        this.creditParty = creditParty;
    }

    public Kyc getSenderKyc() {
        return senderKyc;
    }

    public void setSenderKyc(Kyc senderKyc) {
        this.senderKyc = senderKyc;
    }

    public Kyc getRecipientKyc() {
        return recipientKyc;
    }

    public void setRecipientKyc(Kyc recipientKyc) {
        this.recipientKyc = recipientKyc;
    }

    public String getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(String requestAmount) {
        this.requestAmount = requestAmount;
    }

    public String getRequestCurrency() {
        return requestCurrency;
    }

    public void setRequestCurrency(String requestCurrency) {
        this.requestCurrency = requestCurrency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getChosenDeliveryMethod() {
        return chosenDeliveryMethod;
    }

    public void setChosenDeliveryMethod(String chosenDeliveryMethod) {
        this.chosenDeliveryMethod = chosenDeliveryMethod;
    }

    public Quote[] getQuotes() {
        return quotes;
    }

    public void setQuotes(Quote[] quotes) {
        this.quotes = quotes;
    }

    public String getSenderBlockingReason() {
        return senderBlockingReason;
    }

    public void setSenderBlockingReason(String senderBlockingReason) {
        this.senderBlockingReason = senderBlockingReason;
    }

    public String getRecipientBlockingReason() {
        return recipientBlockingReason;
    }

    public void setRecipientBlockingReason(String recipientBlockingReason) {
        this.recipientBlockingReason = recipientBlockingReason;
    }

    public GsmaParty[] getMetadata() {
        return metadata;
    }

    public void setMetadata(GsmaParty[] metadata) {
        this.metadata = metadata;
    }

    public String getQuotationReference() {
        return quotationReference;
    }

    public void setQuotationReference(String quotationReference) {
        this.quotationReference = quotationReference;
    }

    public String getQuotationStatus() {
        return quotationStatus;
    }

    public void setQuotationStatus(String quotationStatus) {
        this.quotationStatus = quotationStatus;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getAvailableDeliveryMethod() {
        return availableDeliveryMethod;
    }

    public void setAvailableDeliveryMethod(String availableDeliveryMethod) {
        this.availableDeliveryMethod = availableDeliveryMethod;
    }
}

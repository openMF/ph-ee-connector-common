package org.mifos.connector.common.gsma.dto;

public class Bill {

    private String currency;
    private String amountDue;
    private String dueDate;
    private String billReference;
    private String minimumAmountDue;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(String amountDue) {
        this.amountDue = amountDue;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getBillReference() {
        return billReference;
    }

    public void setBillReference(String billReference) {
        this.billReference = billReference;
    }

    public String getMinimumAmountDue() {
        return minimumAmountDue;
    }

    public void setMinimumAmountDue(String minimumAmountDue) {
        this.minimumAmountDue = minimumAmountDue;
    }
}

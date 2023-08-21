package org.mifos.connector.common.identityaccountmapper.dto;

public class PaymentModalityDTO {

    private String paymentModality;
    private String financialAddress;
    private String bankingInstitutionCode;

    public PaymentModalityDTO(String paymentModality, String financialAddress, String bankingInstitutionCode) {
        this.paymentModality = paymentModality;
        this.financialAddress = financialAddress;
        this.bankingInstitutionCode = bankingInstitutionCode;
    }

    public String getPaymentModality() {
        return paymentModality;
    }

    public void setPaymentModality(String paymentModality) {
        this.paymentModality = paymentModality;
    }

    public String getFinancialAddress() {
        return financialAddress;
    }

    public void setFinancialAddress(String financialAddress) {
        this.financialAddress = financialAddress;
    }

    public String getBankingInstitutionCode() {
        return bankingInstitutionCode;
    }

    public void setBankingInstitutionCode(String bankingInstitutionCode) {
        this.bankingInstitutionCode = bankingInstitutionCode;
    }
}

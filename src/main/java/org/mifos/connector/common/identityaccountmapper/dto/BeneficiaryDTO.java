package org.mifos.connector.common.identityaccountmapper.dto;

public class BeneficiaryDTO {

    private String payeeIdentity;
    private String paymentModality;
    private String financialAddress;
    private String bankingInstitutionCode;

    public BeneficiaryDTO(String payeeIdentity, String paymentModality, String financialAddress, String bankingInstitutionCode) {
        this.payeeIdentity = payeeIdentity;
        this.paymentModality = paymentModality;
        this.financialAddress = financialAddress;
        this.bankingInstitutionCode = bankingInstitutionCode;
    }

    public String getBankingInstitutionCode() {
        return bankingInstitutionCode;
    }

    public void setBankingInstitutionCode(String bankingInstitutionCode) {
        this.bankingInstitutionCode = bankingInstitutionCode;
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

    public String getFinancialAddress() {
        return financialAddress;
    }

    public void setFinancialAddress(String financialAddress) {
        this.financialAddress = financialAddress;
    }

}

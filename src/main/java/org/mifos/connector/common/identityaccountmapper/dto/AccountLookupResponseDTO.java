package org.mifos.connector.common.identityaccountmapper.dto;

import java.util.List;

public class AccountLookupResponseDTO {
    private String payeeIdentity;
    private List<PaymentModalityDTO> paymentModalityList;

    public AccountLookupResponseDTO(String payeeIdentity, List<PaymentModalityDTO> paymentModalityList) {
        this.payeeIdentity = payeeIdentity;
        this.paymentModalityList = paymentModalityList;
    }

    public String getPayeeIdentity() {
        return payeeIdentity;
    }

    public void setPayeeIdentity(String payeeIdentity) {
        this.payeeIdentity = payeeIdentity;
    }

    public List<PaymentModalityDTO> getPaymentModalityList() {
        return paymentModalityList;
    }

    public void setPaymentModalityList(List<PaymentModalityDTO> paymentModalityList) {
        this.paymentModalityList = paymentModalityList;
    }

}

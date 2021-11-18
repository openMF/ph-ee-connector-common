package org.mifos.connector.common.channel.dto;

import org.mifos.connector.common.gsma.dto.GsmaParty;
import org.mifos.connector.common.mojaloop.dto.TransactionType;

import java.util.Arrays;

public class TransactionChannelCollectionRequestDTO {

    private GsmaParty[] payer;
    private String amount;
    private TransactionType transactionType;

    public TransactionChannelCollectionRequestDTO() {
    }

    public GsmaParty[] getPayer() {
        return payer;
    }

    public void setPayer(GsmaParty[] payer) {
        this.payer = payer;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "TransactionChannelCollectionRequestDTO{" +
                "payer=" + Arrays.toString(payer) +
                ", amount='" + amount + '\'' +
                ", transactionType=" + transactionType +
                '}';
    }
}

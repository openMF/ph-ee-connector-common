package org.mifos.connector.common.channel.dto;

import org.mifos.connector.common.gsma.dto.GsmaParty;
import org.mifos.connector.common.mojaloop.dto.MoneyData;
import org.mifos.connector.common.mojaloop.dto.TransactionType;

import java.util.Arrays;

public class TransactionChannelC2BRequestDTO {

    private GsmaParty[] payer;
    private MoneyData amount;
    private TransactionType transactionType;

    public TransactionChannelC2BRequestDTO() {}

    public GsmaParty[] getPayer() {
        return payer;
    }

    public void setPayer(GsmaParty[] payer) {
        this.payer = payer;
    }

    public MoneyData getAmount() {
        return amount;
    }

    public void setAmount(MoneyData amount) {
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
        return "TransactionChannelCollectionRequestDTO{" + "payer=" + Arrays.toString(payer) + ", amount='" + amount + '\''
                + ", transactionType=" + transactionType + '}';
    }
}

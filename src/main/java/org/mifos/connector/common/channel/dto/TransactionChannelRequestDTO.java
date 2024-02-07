/*
 * This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at
 *
 *  https://mozilla.org/MPL/2.0/.
 */
package org.mifos.connector.common.channel.dto;

import java.util.List;
import javax.validation.constraints.NotNull;
import org.mifos.connector.common.gsma.dto.CustomData;
import org.mifos.connector.common.mojaloop.dto.*;
import org.mifos.connector.common.mojaloop.type.AmountType;

public class TransactionChannelRequestDTO {

    private String clientRefId;
    @NotNull
    private Party payer;
    private Party payee;
    private AmountType amountType;
    @NotNull
    private MoneyData amount;
    private TransactionType transactionType;
    private GeoCode geoCode;
    private String note;
    private String expiration;
    private ExtensionList extensionList;
    private List<CustomData> customData;

    public List<CustomData> getCustomData() {
        return customData;
    }

    public void setCustomData(List<CustomData> customData) {
        this.customData = customData;
    }

    public String getClientRefId() {
        return clientRefId;
    }

    public void setClientRefId(String clientRefId) {
        this.clientRefId = clientRefId;
    }

    public Party getPayer() {
        return payer;
    }

    public void setPayer(Party payer) {
        this.payer = payer;
    }

    public Party getPayee() {
        return payee;
    }

    public void setPayee(Party payee) {
        this.payee = payee;
    }

    public AmountType getAmountType() {
        return amountType;
    }

    public void setAmountType(AmountType amountType) {
        this.amountType = amountType;
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

    public GeoCode getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(GeoCode geoCode) {
        this.geoCode = geoCode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public ExtensionList getExtensionList() {
        return extensionList;
    }

    public void setExtensionList(ExtensionList extensionList) {
        this.extensionList = extensionList;
    }

    @Override
    public String toString() {
        return "TransactionChannelRequestDTO{" + "clientRefId='" + clientRefId + '\'' + ", payer=" + payer + ", payee=" + payee
                + ", amountType=" + amountType + ", amount=" + amount + ", transactionType=" + transactionType + ", note='" + note + '\''
                + ", expiration='" + expiration + '\'' + ", customData=" + customData + '}';
    }
}

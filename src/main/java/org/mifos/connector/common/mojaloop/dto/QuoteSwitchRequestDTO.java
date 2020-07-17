/*
 * This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at
 *
 *  https://mozilla.org/MPL/2.0/.
 */
package org.mifos.connector.common.mojaloop.dto;

import org.mifos.connector.common.mojaloop.type.AmountType;
import org.mifos.connector.common.util.ContextUtil;

import java.time.LocalDateTime;


public class QuoteSwitchRequestDTO {

    private String transactionId;
    private String transactionRequestId;
    private String quoteId;
    private Party payee;
    private Party payer;
    private AmountType amountType;
    private MoneyData amount;
    private MoneyData fees;
    private TransactionType transactionType;
    private GeoCode geoCode;
    private String note;
    private String expiration;
    private ExtensionList extensionList;

    public QuoteSwitchRequestDTO() {
    }

    public QuoteSwitchRequestDTO(String transactionId, String transactionRequestId, String quoteId, Party payee, Party payer,
                                 AmountType amountType, MoneyData amount, MoneyData fees, TransactionType transactionType,
                                 GeoCode geoCode, String note, LocalDateTime expiration, ExtensionList extensionList) {
        this.transactionId = transactionId;
        this.transactionRequestId = transactionRequestId;
        this.quoteId = quoteId;
        this.payee = payee;
        this.payer = payer;
        this.amountType = amountType;
        this.amount = amount;
        this.fees = fees;
        this.transactionType = transactionType;
        this.geoCode = geoCode;
        this.note = note;
        this.expiration = ContextUtil.formatDate(expiration);
        this.extensionList = extensionList;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionRequestId() {
        return transactionRequestId;
    }

    public void setTransactionRequestId(String transactionRequestId) {
        this.transactionRequestId = transactionRequestId;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public Party getPayee() {
        return payee;
    }

    public void setPayee(Party payee) {
        this.payee = payee;
    }

    public Party getPayer() {
        return payer;
    }

    public void setPayer(Party payer) {
        this.payer = payer;
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

    public MoneyData getFees() {
        return fees;
    }

    public void setFees(MoneyData fees) {
        this.fees = fees;
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
}

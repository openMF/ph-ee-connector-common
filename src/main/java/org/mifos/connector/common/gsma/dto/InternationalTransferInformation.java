/*
 * This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at
 *
 *  https://mozilla.org/MPL/2.0/.
 */

/**
@Author Sidhant Gupta
*/
package org.mifos.connector.common.gsma.dto;

/*
"internationalTransferInformation": {
  "originCountry": "AD",
  "quotationReference": "string",
  "quoteId": "string",
  "receivingCountry": "AD",
  "remittancePurpose": "string",
  "relationshipSender": "string",
  "deliveryMethod": "directtoaccount",
  "senderBlockingReason": "string",
  "recipientBlockingReason": "string"
}
*/

public class InternationalTransferInformation {

    String originCountry;
    String quotationReference;
    String quoteId;
    String receivingCountry;
    String remittancePurpose;
    String relationshipSender;
    String deliveryMethod;
    String senderBlockingReason;
    String recipientBlockingReason;
    String receivingCurrency;
    String senderCurrency;
    String currencyPair;
    String currencyPairRate;
    String receivingAmount;

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getQuotationReference() {
        return quotationReference;
    }

    public void setQuotationReference(String quotationReference) {
        this.quotationReference = quotationReference;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public String getReceivingCountry() {
        return receivingCountry;
    }

    public void setReceivingCountry(String receivingCountry) {
        this.receivingCountry = receivingCountry;
    }

    public String getRemittancePurpose() {
        return remittancePurpose;
    }

    public void setRemittancePurpose(String remittancePurpose) {
        this.remittancePurpose = remittancePurpose;
    }

    public String getRelationshipSender() {
        return relationshipSender;
    }

    public void setRelationshipSender(String relationshipSender) {
        this.relationshipSender = relationshipSender;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
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

    public String getReceivingCurrency() {
        return receivingCurrency;
    }

    public void setReceivingCurrency(String receivingCurrency) {
        this.receivingCurrency = receivingCurrency;
    }

    public String getSenderCurrency() {
        return senderCurrency;
    }

    public void setSenderCurrency(String senderCurrency) {
        this.senderCurrency = senderCurrency;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    public String getCurrencyPairRate() {
        return currencyPairRate;
    }

    public void setCurrencyPairRate(String currencyPairRate) {
        this.currencyPairRate = currencyPairRate;
    }

    public String getReceivingAmount() {
        return receivingAmount;
    }

    public void setReceivingAmount(String receivingAmount) {
        this.receivingAmount = receivingAmount;
    }
}

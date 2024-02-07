/*
 * This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at
 *
 *  https://mozilla.org/MPL/2.0/.
 */
package org.mifos.connector.common.mojaloop.dto;

import org.mifos.connector.common.util.ContextUtil;

import java.time.LocalDateTime;

public class TransferSwitchRequestDTO {

    private String transferId;
    private String payerFsp;
    private String payeeFsp;
    private MoneyData amount;
    private String ilpPacket;
    private String condition;
    private String expiration;
    private ExtensionList extensionList;

    public TransferSwitchRequestDTO() {}

    public TransferSwitchRequestDTO(String transferId, String payerFsp, String payeeFsp, MoneyData amount, String ilpPacket,
            String condition, LocalDateTime expiration, ExtensionList extensionList) {
        this.transferId = transferId;
        this.payerFsp = payerFsp;
        this.payeeFsp = payeeFsp;
        this.amount = amount;
        this.ilpPacket = ilpPacket;
        this.condition = condition;
        this.expiration = ContextUtil.formatDate(expiration);
        this.extensionList = extensionList;
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public String getPayerFsp() {
        return payerFsp;
    }

    public void setPayerFsp(String payerFsp) {
        this.payerFsp = payerFsp;
    }

    public String getPayeeFsp() {
        return payeeFsp;
    }

    public void setPayeeFsp(String payeeFsp) {
        this.payeeFsp = payeeFsp;
    }

    public MoneyData getAmount() {
        return amount;
    }

    public void setAmount(MoneyData amount) {
        this.amount = amount;
    }

    public String getIlpPacket() {
        return ilpPacket;
    }

    public void setIlpPacket(String ilpPacket) {
        this.ilpPacket = ilpPacket;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
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

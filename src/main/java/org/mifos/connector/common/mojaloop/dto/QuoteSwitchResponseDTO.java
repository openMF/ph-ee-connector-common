/*
 * This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at
 *
 *  https://mozilla.org/MPL/2.0/.
 */
package org.mifos.connector.common.mojaloop.dto;

import java.time.LocalDateTime;
import org.mifos.connector.common.util.ContextUtil;

public class QuoteSwitchResponseDTO {

    private MoneyData transferAmount; // mandatory
    private MoneyData payeeReceiveAmount;
    private MoneyData payeeFspFee;
    private MoneyData payeeFspCommission;
    private String expiration; // mandatory
    private GeoCode geoCode;
    private String ilpPacket; // mandatory
    private String condition; // mandatory
    private ExtensionList extensionList;

    public QuoteSwitchResponseDTO() {}

    public QuoteSwitchResponseDTO(MoneyData transferAmount, MoneyData payeeReceiveAmount, MoneyData payeeFspFee,
            MoneyData payeeFspCommission, LocalDateTime expiration, GeoCode geoCode, String ilpPacket, String condition,
            ExtensionList extensionList) {
        this.transferAmount = transferAmount;
        this.payeeReceiveAmount = payeeReceiveAmount;
        this.payeeFspFee = payeeFspFee;
        this.payeeFspCommission = payeeFspCommission;
        this.expiration = ContextUtil.formatDate(expiration);
        this.geoCode = geoCode;
        this.ilpPacket = ilpPacket;
        this.condition = condition;
        this.extensionList = extensionList;
    }

    public MoneyData getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(MoneyData transferAmount) {
        this.transferAmount = transferAmount;
    }

    public MoneyData getPayeeReceiveAmount() {
        return payeeReceiveAmount;
    }

    public void setPayeeReceiveAmount(MoneyData payeeReceiveAmount) {
        this.payeeReceiveAmount = payeeReceiveAmount;
    }

    public MoneyData getPayeeFspFee() {
        return payeeFspFee;
    }

    public void setPayeeFspFee(MoneyData payeeFspFee) {
        this.payeeFspFee = payeeFspFee;
    }

    public MoneyData getPayeeFspCommission() {
        return payeeFspCommission;
    }

    public void setPayeeFspCommission(MoneyData payeeFspCommission) {
        this.payeeFspCommission = payeeFspCommission;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public void setExtensionList(ExtensionList extensionList) {
        this.extensionList = extensionList;
    }

    public GeoCode getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(GeoCode geoCode) {
        this.geoCode = geoCode;
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
}

/*
 * This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at
 *
 *  https://mozilla.org/MPL/2.0/.
 */
package org.mifos.connector.common.mojaloop.dto;

import javax.validation.constraints.NotNull;

/**
 * {
 * accountId: ""
 * }
 */
public class Party {

    @NotNull
    private PartyIdInfo partyIdInfo; // mandatory
    private String merchantClassificationCode; // optional
    private String name; // optional
    private PersonalInfo personalInfo; // optional

    Party() {
    }

    public Party(PartyIdInfo partyIdInfo, String merchantClassificationCode, String name, PersonalInfo personalInfo) {
        this.partyIdInfo = partyIdInfo;
        this.merchantClassificationCode = merchantClassificationCode;
        this.name = name;
        this.personalInfo = personalInfo;
    }

    public Party(PartyIdInfo partyIdInfo) {
        this(partyIdInfo, null, null, null);
    }

    public PartyIdInfo getPartyIdInfo() {
        return partyIdInfo;
    }

    public void setPartyIdInfo(PartyIdInfo partyIdInfo) {
        this.partyIdInfo = partyIdInfo;
    }

    public String getMerchantClassificationCode() {
        return merchantClassificationCode;
    }

    public void setMerchantClassificationCode(String merchantClassificationCode) {
        this.merchantClassificationCode = merchantClassificationCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public void update(Party oParty) {
        if (oParty == null)
            return;

        update(oParty.partyIdInfo);

        String oMerchantCode = oParty.merchantClassificationCode;
        if (oMerchantCode != null) {
            merchantClassificationCode = oMerchantCode;
        }

        String oName = oParty.name;
        if (oName == null) {
            name = oName;
        }

        PersonalInfo oInfo = oParty.personalInfo;
        if (personalInfo == null)
            personalInfo = oInfo;
        else
            personalInfo.update(oInfo);
    }

    public void update(PartyIdInfo oPartyIdInfo) {
        if (oPartyIdInfo == null)
            return;

        partyIdInfo.update(oPartyIdInfo);
    }
}

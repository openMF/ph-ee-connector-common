/*
 * This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at
 *
 *  https://mozilla.org/MPL/2.0/.
 */
package org.mifos.phee.common.channel.dto;

import com.ilp.conditions.models.pdp.PartyComplexName;
import com.ilp.conditions.models.pdp.PartyPersonalInfo;
import org.mifos.phee.common.mojaloop.dto.ComplexName;
import org.mifos.phee.common.mojaloop.dto.PartyIdInfo;
import org.mifos.phee.common.mojaloop.dto.PersonalInfo;

import java.beans.Transient;

/**
 * {
 * 	accountId: ""
 * }
 */
public class ChannelParty {

    private ChannelPartyIdInfo partyIdInfo; // mandatory
    private String merchantClassificationCode ; // optional
    private String name; // optional
    private PersonalInfo personalInfo; // optional

    public ChannelParty() {
    }

    public ChannelParty(ChannelPartyIdInfo partyIdInfo, String merchantClassificationCode, String name, PersonalInfo personalInfo) {
        this.partyIdInfo = partyIdInfo;
        this.merchantClassificationCode = merchantClassificationCode;
        this.name = name;
        this.personalInfo = personalInfo;
    }

    public ChannelPartyIdInfo getPartyIdInfo() {
        return partyIdInfo;
    }

    public void setPartyIdInfo(ChannelPartyIdInfo partyIdInfo) {
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
}

package org.mifos.phee.common.channel.dto;

import org.mifos.phee.common.mojaloop.type.IdentifierType;

public class ChannelPartyIdInfo {

    private IdentifierType partyIdType; // mandatory, immutable
    private String partyIdentifier; // mandatory, immutable
    private String partySubIdOrType; // optional, immutable
    private String fspId; // optional
    private String tenantId;

    public ChannelPartyIdInfo() {
    }

    public ChannelPartyIdInfo(IdentifierType partyIdType, String partyIdentifier, String partySubIdOrType, String fspId, String tenantId) {
        this.partyIdType = partyIdType;
        this.partyIdentifier = partyIdentifier;
        this.partySubIdOrType = partySubIdOrType;
        this.fspId = fspId;
        this.tenantId = tenantId;
    }

    public IdentifierType getPartyIdType() {
        return partyIdType;
    }

    public void setPartyIdType(IdentifierType partyIdType) {
        this.partyIdType = partyIdType;
    }

    public String getPartyIdentifier() {
        return partyIdentifier;
    }

    public void setPartyIdentifier(String partyIdentifier) {
        this.partyIdentifier = partyIdentifier;
    }

    public String getPartySubIdOrType() {
        return partySubIdOrType;
    }

    public void setPartySubIdOrType(String partySubIdOrType) {
        this.partySubIdOrType = partySubIdOrType;
    }

    public String getFspId() {
        return fspId;
    }

    public void setFspId(String fspId) {
        this.fspId = fspId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}

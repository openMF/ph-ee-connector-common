package org.mifos.mojaloop;

public class Dfsp {

    private boolean enabled;
    private String id, partyIdType, partyIdentifier, fundsInPrepareAmount, domain;

    public Dfsp() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartyIdType() {
        return partyIdType;
    }

    public void setPartyIdType(String partyIdType) {
        this.partyIdType = partyIdType;
    }

    public String getPartyIdentifier() {
        return partyIdentifier;
    }

    public void setPartyIdentifier(String partyIdentifier) {
        this.partyIdentifier = partyIdentifier;
    }

    public String getFundsInPrepareAmount() {
        return fundsInPrepareAmount;
    }

    public void setFundsInPrepareAmount(String fundsInPrepareAmount) {
        this.fundsInPrepareAmount = fundsInPrepareAmount;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}

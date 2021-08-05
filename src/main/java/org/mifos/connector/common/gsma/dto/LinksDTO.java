package org.mifos.connector.common.gsma.dto;

public class LinksDTO {

    private String linkReference;
    private GsmaParty[] sourceAccountIdentifiers;
    private String status;
    private String mode;

    public String getLinkReference() {
        return linkReference;
    }

    public void setLinkReference(String linkReference) {
        this.linkReference = linkReference;
    }

    public GsmaParty[] getSourceAccountIdentifiers() {
        return sourceAccountIdentifiers;
    }

    public void setSourceAccountIdentifiers(GsmaParty[] sourceAccountIdentifiers) {
        this.sourceAccountIdentifiers = sourceAccountIdentifiers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

}

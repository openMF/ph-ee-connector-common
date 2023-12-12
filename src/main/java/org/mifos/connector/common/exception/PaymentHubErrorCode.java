package org.mifos.connector.common.exception;

public enum PaymentHubErrorCode {
    BUSINESSRULE("BusinessRule"),

    VALIDATION("Validation"),

    AUTHORISATION("Authorisation"),

    IDENTIFICATION("Identification"),

    INTERNAL("Internal"),

    SERVICEUNAVAILABLE("Service Unavailable"),
    TOOMANYREQUEST("Too many requests");

    private String value;

    PaymentHubErrorCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

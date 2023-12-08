package org.mifos.connector.common.Validation;

public enum ValidatorEnums implements ValidatorCodeAndMessage {
    INVALID_LENGTH("INVALID_LENGTH", "length is invalid"),
    INVALID_LIST("INVALID_LIST", "list is invalid"),
    INVALID_NEGATIVE_FIELD("INVALID_NEGATIVE_FIELD", "this field cannot be negative"),
    INVALID_MAX_LENGTH("INVALID_MAX_LENGTH", "cannot exceed the maximum length");

    private final String code;
    private final String message;

    ValidatorEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
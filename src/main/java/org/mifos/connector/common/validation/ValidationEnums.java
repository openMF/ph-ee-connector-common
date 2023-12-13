package org.mifos.connector.common.validation;

import org.mifos.connector.common.exception.PaymentHubErrorCode;

public enum ValidationEnums implements ValidationCodeType {
    INVALID_LENGTH("INVALID_LENGTH", PaymentHubErrorCode.VALIDATION.getValue(), "length is invalid"),
    INVALID_LIST("INVALID_LIST", PaymentHubErrorCode.VALIDATION.getValue(), "list is invalid"),
    INVALID_NEGATIVE_FIELD("INVALID_NEGATIVE_FIELD", PaymentHubErrorCode.VALIDATION.getValue(), "this field cannot be negative"),
    INVALID_MAX_LENGTH("INVALID_MAX_LENGTH", PaymentHubErrorCode.VALIDATION.getValue(), "cannot exceed the maximum length");

    private final String code;
    private final String category;
    private final String message;

    ValidationEnums(String code, String category, String message) {
        this.code = code;
        this.category = category;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getCategory() {
        return this.category;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
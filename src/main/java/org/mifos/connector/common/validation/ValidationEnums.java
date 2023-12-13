package org.mifos.connector.common.validation;

import org.mifos.connector.common.exception.PaymentHubErrorCategory;

public enum ValidationEnums implements ValidationCodeType {
    INVALID_LENGTH("INVALID_LENGTH", PaymentHubErrorCategory.Validation.toString(), "length is invalid"),
    INVALID_LIST("INVALID_LIST", PaymentHubErrorCategory.Validation.toString(), "list is invalid"),
    INVALID_NEGATIVE_FIELD("INVALID_NEGATIVE_FIELD", PaymentHubErrorCategory.Validation.toString(), "this field cannot be negative"),
    INVALID_MAX_LENGTH("INVALID_MAX_LENGTH", PaymentHubErrorCategory.Validation.toString(), "cannot exceed the maximum length");

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
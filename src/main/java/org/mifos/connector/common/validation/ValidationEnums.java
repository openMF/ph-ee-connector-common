package org.mifos.connector.common.validation;

import org.mifos.connector.common.exception.PaymentHubErrorCategory;

public enum ValidationEnums implements ValidationCodeType {
    INVALID_LENGTH("INVALID_LENGTH", "length is invalid"),
    INVALID_LIST("INVALID_LIST", "list is invalid"),
    INVALID_NEGATIVE_FIELD("INVALID_NEGATIVE_FIELD",  "this field cannot be negative"),
    INVALID_MAX_LENGTH("INVALID_MAX_LENGTH", "cannot exceed the maximum length");

    private final String code;
    private final String category;
    private final String message;

    ValidationEnums(String code, String message) {
        this.code = code;
        this.category = PaymentHubErrorCategory.Validation.toString();
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
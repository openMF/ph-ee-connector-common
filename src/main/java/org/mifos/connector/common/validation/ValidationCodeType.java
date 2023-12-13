package org.mifos.connector.common.validation;

import org.mifos.connector.common.exception.PaymentHubErrorCategory;

public interface ValidationCodeType {

    String getCode();
    default String getCategory(){
        return PaymentHubErrorCategory.Validation.toString();
    }
    String getMessage();
}
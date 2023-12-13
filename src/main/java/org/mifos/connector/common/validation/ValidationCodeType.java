package org.mifos.connector.common.validation;

import org.mifos.connector.common.exception.PaymentHubErrorCategory;

public interface ValidationCodeType {

    String getCode();
    String getCategory();
    String getMessage();
}
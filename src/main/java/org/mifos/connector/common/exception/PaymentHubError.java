package org.mifos.connector.common.exception;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum PaymentHubError {

    // Interoperability Errors;
    IdNotFound(PaymentHubErrorCategory.Interop, "IdNotFound", "Generic ID not found"),
    PayerFSPIdNotFound(PaymentHubErrorCategory.Interop, "PayerFSPIdNotFound", "Payer FSP ID not found"),
    PayeeFSPIdNotFound(PaymentHubErrorCategory.Interop, "PayeeFSPIdNotFound", "Payee FSP ID not found"),
    PayerNotFound(PaymentHubErrorCategory.Interop, "PayerNotFound", "Payer not found"),
    PayeeNotFound(PaymentHubErrorCategory.Interop, "PayeeNotFound", "Payee not found"),
    CurrencyPairNotFound(PaymentHubErrorCategory.Interop, "CurrencyPairNotFound", "Currency pair not found"),
    TransactionIdNotFound(PaymentHubErrorCategory.Interop, "TransactionIdNotFound", "Transaction ID not found"),
    TransferIdNotFound(PaymentHubErrorCategory.Interop, "TransferIdNotFound", "Transfer ID not found"),
    InactiveAccountStatus(PaymentHubErrorCategory.Interop, "InactiveAccountStatus", "Inactive account status"),
    PayerCurrencyInvalid(PaymentHubErrorCategory.Interop, "PayerCurrencyInvalid", "Payer currency invalid"),
    PayeeCurrencyInvalid(PaymentHubErrorCategory.Interop, "PayeeCurrencyInvalid", "Payee currency invalid"),
    PayeeFSPTransactionTypeInvalid(PaymentHubErrorCategory.Interop, "PayeeFSPTransactionTypeInvalid", "PayeeFSPTransactionTypeInvalid");

    private PaymentHubErrorCategory errorCategory;
    private String errorCode;
    private String errorDescription;

    PaymentHubError(PaymentHubErrorCategory errorCategory, String code, String errorMessage) {
        this.errorCategory = errorCategory;
        this.errorCode = code;
        this.errorDescription = errorMessage;
    }

    public static PaymentHubErrorCategory getCategory(String errorCode) {
        return fromCode(errorCode).getErrorCategory();
    }

    public static PaymentHubError fromCode(String code) {
        return Arrays.stream(values())
                .filter(ec -> ec.getErrorCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can not get unknown errorCode: " + code));
    }

    public static List<PaymentHubError> fromCategory(PaymentHubErrorCategory errorCategory) {
        return Arrays.stream(values())
                .filter(ec -> ec.errorCategory == errorCategory)
                .collect(Collectors.toList());
    }
}

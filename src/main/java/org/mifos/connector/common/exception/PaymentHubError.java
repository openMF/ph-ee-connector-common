package org.mifos.connector.common.exception;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum PaymentHubError {

    // Interoperability Errors;
    CommunicationError(PaymentHubErrorCategory.Interop, "CommunicationError", "Communication error"),
    DestinationCommunicationError(PaymentHubErrorCategory.Interop, "DestinationCommunicationError", "Destination communication error"),
    ExtGenericServerError(PaymentHubErrorCategory.Interop, "ExtGenericServerError", "Generic server error"),
    ExtInternalServerError(PaymentHubErrorCategory.Interop, "ExtInternalServerError", "Internal server error"),
    NotImplemented(PaymentHubErrorCategory.Interop, "NotImplemented", "Not implemented"),
    ServiceDenied(PaymentHubErrorCategory.Interop, "ServiceDenied", "Service currently unavailable"),
    TimeOut(PaymentHubErrorCategory.Interop, "TimeOut", "Server timed-out"),
    ConnectionReset(PaymentHubErrorCategory.Interop, "ConnectionReset", "Connection reset by server/ request cancelled"),
    GenericClientError(PaymentHubErrorCategory.Interop, "GenericClientError", "Generic client error"),
    UnknownURL(PaymentHubErrorCategory.Interop, "UnknownURL", "Unknown URI"),
    AddPartyError(PaymentHubErrorCategory.Interop, "AddPartyError", "Add Party information error"),
    ExtValidationError(PaymentHubErrorCategory.Interop, "ExtValidationError", "Generic validation error"),
    SyntaxError(PaymentHubErrorCategory.Interop, "SyntaxError", "Malformed syntax"),
    RequiredElement(PaymentHubErrorCategory.Interop, "RequiredElement", "Missing mandatory element"),
    IdNotFound(PaymentHubErrorCategory.Interop, "IdNotFound", "Generic ID not found"),
    PayerFSPIdNotFound(PaymentHubErrorCategory.Interop, "PayerFSPIdNotFound", "Payer FSP ID not found"),
    PayeeFSPIdNotFound(PaymentHubErrorCategory.Interop, "PayeeFSPIdNotFound", "Payee FSP ID not found"),
    PayerNotFound(PaymentHubErrorCategory.Interop, "PayerNotFound", "Payer not found"),
    PayeeNotFound(PaymentHubErrorCategory.Interop, "PayeeNotFound", "Payee not found"),
    CurrencyPairNotFound(PaymentHubErrorCategory.Interop, "CurrencyPairNotFound", "Currency pair not found"),
    TransactionIdNotFound(PaymentHubErrorCategory.Interop, "TransactionIdNotFound", "Transaction ID not found"),
    TransferIdNotFound(PaymentHubErrorCategory.Interop, "TransferIdNotFound", "Transfer ID not found"),
    InactiveAccountStatus(PaymentHubErrorCategory.Interop, "InactiveAccountStatus", "Inactive account status"),
    TransferExpired(PaymentHubErrorCategory.Interop, "TransferExpired", "Transfer expired"),
    PayerCurrencyInvalid(PaymentHubErrorCategory.Interop, "PayerCurrencyInvalid", "Payer currency invalid"),
    GenericPayerError(PaymentHubErrorCategory.Interop, "GenericPayerError", "Generic Payer error"),
    PayerFSPTransactionTypeInvalid(PaymentHubErrorCategory.Interop, "PayerFSPTransactionTypeInvalid", "Payer FSP Unsupported Transaction type"),
    PayerInsufficientBalance(PaymentHubErrorCategory.Interop, "PayerInsufficientBalance", "Payer Party Insufficient Balance"),
    PayeeCurrencyInvalid(PaymentHubErrorCategory.Interop, "PayeeCurrencyInvalid", "Payee currency invalid"),
    PayeeUnauthorized(PaymentHubErrorCategory.Interop, "PayeeUnauthorized", "Payee permission error"),
    PayeeFSPTransactionTypeInvalid(PaymentHubErrorCategory.Interop, "PayeeFSPTransactionTypeInvalid", "Payee FSP unsupported transaction type"),
    PayerFspNotConfigured(PaymentHubErrorCategory.Interop, "PayerFspNotConfigured", "Payer FSP not configured"),
    PayeeFspNotConfigured(PaymentHubErrorCategory.Interop, "PayeeFspNotConfigured", "Payee FSP not configured"),
    // validation errors
    FormatError(PaymentHubErrorCategory.Validation, "FormatError", "The specified property contents do not conform to the format required for this Property."),
    NegativeValue(PaymentHubErrorCategory.Validation, "NegativeValue", "The amount supplied is negative and this is not allowed for the given service."),
    MandatoryValueNotSupplied(PaymentHubErrorCategory.Validation, "MandatoryValueNotSupplied", "A mandatory value has not been provided in the header and/or JSON body."),
    SamePartiesError(PaymentHubErrorCategory.BusinessRule, "SamePartiesError", "The debit and credit parties are the same.");

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

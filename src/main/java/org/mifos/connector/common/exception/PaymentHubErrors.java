package org.mifos.connector.common.exception;

import java.util.Arrays;

public enum PaymentHubErrors {

    // Interoperability Errors;
    IdNotFound("IdNotFound", "Generic ID not found"),
    PayerFSPIdNotFound("PayerFSPIdNotFound", "Payer FSP ID not found"),
    PayeeFSPIdNotFound("PayeeFSPIdNotFound", "Payee FSP ID not found"),
    PayerNotFound("PayerNotFound", "Payer not found"),
    PayeeNotFound("PayeeNotFound", "Payee not found"),
    CurrencyPairNotFound("CurrencyPairNotFound", "Currency pair not found"),
    TransactionIdNotFound("TransactionIdNotFound", "Transaction ID not found"),
    TransferIdNotFound("TransferIdNotFound", "Transfer ID not found"),
    InactiveAccountStatus("InactiveAccountStatus", "Inactive account status"),
    PayerCurrencyInvalid("PayerCurrencyInvalid", "Payer currency invalid"),
    PayeeCurrencyInvalid("PayeeCurrencyInvalid", "Payee currency invalid"),
    PayeeFSPTransactionTypeInvalid("PayeeFSPTransactionTypeInvalid", "PayeeFSPTransactionTypeInvalid");

    private String errorCode;
    private String errorDescription;

    PaymentHubErrors(String code, String errorMessage) {
        this.errorCode = code;
        this.errorDescription = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public static PaymentHubErrors fromCode(String code) {
        return Arrays.stream(values())
                .filter(ec -> ec.getErrorCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can not get unknown errorCode: " + code));
    }
}

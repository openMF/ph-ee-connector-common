package org.mifos.connector.common.exception.mapper;

import org.mifos.connector.common.exception.PaymentHubError;

import java.util.HashMap;
import java.util.Map;

/**
 * Default implementation of mapper. This class can be used in ams or payment schema connector for creating there
 * respective mappings of external error codes.
 */
public abstract class ErrorMapper implements Mapper {

    private Map<String, PaymentHubError> errorMap = new HashMap<>();

    public ErrorMapper() {
        configure();
    }

    @Override
    public void add(String externalErrorCode, PaymentHubError internalError) {
        errorMap.put(externalErrorCode, internalError);
    }

    @Override
    public void add(String externalErrorCode, String internalError) {
        errorMap.put(externalErrorCode, PaymentHubError.fromCode(internalError));
    }

    @Override
    public PaymentHubError getInternalError(String externalErrorCode) {
        return errorMap.get(externalErrorCode);
    }

    @Override
    public String getExternalError(String internalErrorCode) {
        PaymentHubError paymentHubErrors = PaymentHubError.fromCode(internalErrorCode);
        PaymentHubError filterResult = errorMap.values().stream().filter(paymentHubErrors::equals).findFirst()
                .orElseThrow(() -> new RuntimeException("Can not get external error code for internal error code: " + internalErrorCode));
        return filterResult.getErrorCode();
    }

    @Override
    public String getExternalError(PaymentHubError internalErrorCode) {
        PaymentHubError filterResult = errorMap.values().stream().filter(internalErrorCode::equals).findFirst()
                .orElseThrow(() -> new RuntimeException("Can not get external error code for internal error code: " + internalErrorCode));
        return filterResult.getErrorCode();
    }
}

package org.mifos.connector.common.exception.mapper;

import org.mifos.connector.common.exception.PaymentHubErrors;

import java.util.HashMap;
import java.util.Map;

/**
 * Default implementation of mapper. This class can be used in ams or
 * payment schema connector for creating there respective mappings of
 * external error codes.
 */
public abstract class ErrorMapper implements Mapper {

    private Map<String, PaymentHubErrors> errorMap = new HashMap<>();

    public ErrorMapper() {
        configure();
    }

    @Override
    public void add(String externalErrorCode, PaymentHubErrors internalError) {
        errorMap.put(externalErrorCode, internalError);
    }

    @Override
    public void add(String externalErrorCode, String internalError) {
        errorMap.put(externalErrorCode, PaymentHubErrors.fromCode(internalError));
    }

    @Override
    public PaymentHubErrors getInternalError(String externalErrorCode) {
        return errorMap.get(externalErrorCode);
    }

    @Override
    public String getExternalError(String internalErrorCode) {
        PaymentHubErrors paymentHubErrors = PaymentHubErrors.fromCode(internalErrorCode);
        PaymentHubErrors filterResult = errorMap.values()
                .stream()
                .filter(paymentHubErrors::equals)
                .findFirst().orElseThrow(() ->
                        new RuntimeException("Can not get external error code for internal error code: " +
                                internalErrorCode));
        return filterResult.getErrorCode();
    }

    @Override
    public String getExternalError(PaymentHubErrors internalErrorCode) {
        PaymentHubErrors filterResult = errorMap.values()
                .stream()
                .filter(internalErrorCode::equals)
                .findFirst().orElseThrow(() ->
                        new RuntimeException("Can not get external error code for internal error code: " +
                                internalErrorCode));
        return filterResult.getErrorCode();
    }
}

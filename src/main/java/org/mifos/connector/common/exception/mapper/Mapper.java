package org.mifos.connector.common.exception.mapper;

import org.mifos.connector.common.exception.PaymentHubError;

/**
 * Interface for creating external error code mapper
 */
public interface Mapper {

    // adds the mapping of externalErrorCode - [PaymentHubErrors]
    void add(String externalErrorCode, PaymentHubError internalError);

    // adds the mapping of externalErrorCode - [PaymentHubErrors] (fetches enum using the string passed)
    void add(String externalErrorCode, String internalError);

    // getting the internal error code using the external error code
    PaymentHubError getInternalError(String externalErrorCode);

    // getting the external error code using the internal error code
    String getExternalError(String internalErrorCode);

    // getting the external error code using the internal error code enum
    String getExternalError(PaymentHubError internalErrorCode);

    // method to configure all the mappings
    void configure();
}

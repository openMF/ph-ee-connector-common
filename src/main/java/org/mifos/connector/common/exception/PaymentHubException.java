package org.mifos.connector.common.exception;

import lombok.Getter;

@Getter
public class PaymentHubException extends Exception {

    private String errorCode;
    private String errorDescription;

    private PaymentHubException(PhExceptionBuilder builder) {
        this.errorCode = builder.errorCode;
        this.errorDescription = builder.errorDescription;
    }

    public void throwException() throws PaymentHubException {
        throw this;
    }

    /**
     * Builder class for PaymentHubException
     *
     * Ways to create an exception object
     *
     * 1. Set error code and description (Can be used for creating custom errors)
     * new PaymentHubException.PhExceptionBuilder()
     *                 .withErrorCode(ec)
     *                 .withErrorDescription(ed)
     *                 .build()
     *
     * 2. Set [PaymentHubErrors] enum (Can be used for creating predefined errors)
     * new PaymentHubException.PhExceptionBuilder()
     *                 .withErrorInformation(PaymentHubErrors.PayerFSPIdNotFound)
     *                 .build();
     *
     * 3. Set error code (can be used when error code belong to existing list of errors)
     * new PaymentHubException.PhExceptionBuilder()
     *                 .withErrorCode("PayerFSPIdNotFound")
     *                 .build();
     *
     */
    public static class PhExceptionBuilder {
        private String errorCode;
        private String errorDescription;

        public PhExceptionBuilder withErrorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public PhExceptionBuilder withErrorDescription(String errorInformation) {
            this.errorDescription = errorInformation;
            return this;
        }

        public PhExceptionBuilder withErrorInformation(PaymentHubError errorInformation) {
            this.errorCode = errorInformation.getErrorCode();
            this.errorDescription = errorInformation.getErrorDescription();
            return this;
        }

        public PaymentHubException build() {
            validate();
            return new PaymentHubException(this);
        }

        // builds and throws an PaymentHubException
        public void throwException() throws PaymentHubException {
            build().throwException();
        }

        // validates the fields and fetches default values for error code
        private void validate() {
            if (errorCode == null) {
                throw new IllegalArgumentException("Error code is required");
            }
            if (errorDescription == null) {
                PaymentHubError paymentHubErrors = PaymentHubError.fromCode(this.errorCode);
                this.errorCode = paymentHubErrors.getErrorCode();
                this.errorDescription = paymentHubErrors.getErrorDescription();
            }
        }
    }

}

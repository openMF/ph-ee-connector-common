package org.mifos.connector.common.channel.dto;

import lombok.Getter;
import lombok.ToString;
import org.mifos.connector.common.exception.PaymentHubError;
import org.mifos.connector.common.exception.PaymentHubException;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class PhErrorDTO {

    @NotNull
    private String errorCategory;
    @NotNull
    private String errorCode;
    @NotNull
    private String errorDescription;
    private String developerMessage;
    private String defaultUserMessage;
    private List<ErrorParameter> errorParameters;

    private PhErrorDTO(PhErrorDTOBuilder builder) {
        this.errorCategory = builder.errorCategory;
        this.errorCode = builder.errorCode;
        this.errorDescription = builder.errorDescription;
        this.developerMessage = builder.developerMessage;
        this.defaultUserMessage = builder.defaultUserMessage;
        this.errorParameters = builder.errorParameters;
    }

    /**
     * Creates builder to build {@link PhErrorDTO}.
     *
     * Example:
     * new PhErrorDTOBuilder(PaymentHubError.IdNotFound).build()
     *
     * new PhErrorDTOBuilder("idnotfound).build()
     *
     * new PhErrorDTOBuilder(PaymentHubError.IdNotFound)
     *     .addErrorParameter("accountId", "1231231").build()
     */
    public static class PhErrorDTOBuilder {

        private String errorCategory;
        private String errorCode;
        private String errorDescription;
        private String developerMessage;
        private String defaultUserMessage;
        private List<ErrorParameter> errorParameters;

        // sets not null fields using [PaymentHubError] object
        public PhErrorDTOBuilder(PaymentHubError error) {
            setupUsingPaymentHubErrorObject(error);
        }

        // sets not null fields using [PaymentHubException] object
        public PhErrorDTOBuilder(PaymentHubException exception) {
            PaymentHubError paymentHubError = PaymentHubError.fromCode(exception.getErrorCode());
            setupUsingPaymentHubErrorObject(paymentHubError);
        }

        // sets not null fields using valid error code in [String] format
        public PhErrorDTOBuilder(String errorCode) {
            PaymentHubError paymentHubError = PaymentHubError.fromCode(errorCode);
            setupUsingPaymentHubErrorObject(paymentHubError);
        }

        // method to set the not null fields using [PaymentHubError] object
        private void setupUsingPaymentHubErrorObject(PaymentHubError error) {
            this.errorCategory = error.getErrorCategory().name();
            this.errorCode = error.getErrorCode();
            this.errorDescription = error.getErrorDescription();
        }

        // validates the not null fields
        private void validate() {
            if (this.errorCode == null) {
                throw new IllegalArgumentException("Error code is required");
            }
            if (this.defaultUserMessage == null) {
                this.defaultUserMessage = "";
            }
            if (this.developerMessage == null) {
                this.developerMessage = "";
            }
        }

        // sets the developer message
        public PhErrorDTOBuilder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        // sets the default user message
        public PhErrorDTOBuilder defaultUserMessage(String defaultUserMessage) {
            this.defaultUserMessage = defaultUserMessage;
            return this;
        }

        public PhErrorDTOBuilder addErrorParameter(String key, String value) {
            if (this.errorParameters == null) {
                this.errorParameters = new ArrayList<>();
            }
            this.errorParameters.add(new ErrorParameter(key, value));
            return this;
        }

        // validates and builds the PhErrorDTO object
        public PhErrorDTO build() {
            validate();
            return new PhErrorDTO(this);
        }

    }
}

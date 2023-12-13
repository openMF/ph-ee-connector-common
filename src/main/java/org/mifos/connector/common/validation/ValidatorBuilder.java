package org.mifos.connector.common.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.mifos.connector.common.channel.dto.ErrorParameter;
import org.mifos.connector.common.channel.dto.Errors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class ValidatorBuilder {
    private String errorCategory;
    private String errorCode;
    private String errorDescription;
    private String developerMessage;
    private String defaultUserMessage;
    private String resource;
    private String parameter;
    private Object value;
    private List<ErrorParameter> errorParameters;
    private List<Errors> errorsList;
    private boolean ignoreNullValue = false;

    public ValidatorBuilder() {
        this(new ArrayList<>());
    }

    public ValidatorBuilder(List<Errors> errorsList) {
        this.errorsList = errorsList;
    }

    public ValidatorBuilder reset() {
        return new ValidatorBuilder(this.errorsList);
    }

    public void merge(ValidatorBuilder other) {
        errorsList.addAll(other.errorsList);
    }

    public boolean hasError() {
        return !errorsList.isEmpty();
    }

    public List<Errors> getErrorsList() {
        return errorsList;
    }

    public ValidatorBuilder errorCategory(final String errorCategory) {
        this.errorCategory = errorCategory;
        return this;
    }

    public ValidatorBuilder errorCode(final String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public ValidatorBuilder errorDescription(final String errorDescription) {
        this.errorDescription = errorDescription;
        return this;
    }

    public ValidatorBuilder developerMessage(final String developerMessage) {
        this.developerMessage = developerMessage;
        return this;
    }

    public ValidatorBuilder defaultUserMessage(final String defaultUserMessage) {
        this.defaultUserMessage = defaultUserMessage;
        return this;
    }


    public ValidatorBuilder resource(final String resource) {
        this.resource = resource;
        return this;
    }

    public ValidatorBuilder parameter(final String parameter) {
        this.parameter = parameter;
        return this;
    }

    public ValidatorBuilder value(final Object value) {
        this.value = value;
        return this;
    }

    public ValidatorBuilder errorParameters(final List<ErrorParameter> errorParameters) {
        this.errorParameters = errorParameters;
        return this;
    }

    public ValidatorBuilder ignoreIfNull() {
        this.ignoreNullValue = true;
        return this;
    }

    public Boolean isNullOrEmpty() {
        if (this.value == null) return true;

        if (this.value instanceof String) {
            return ((String) this.value).isEmpty();
        } else if (this.value instanceof List) {
            return ((List<?>) this.value).isEmpty();
        }
        return false;
    }

    public ValidatorBuilder isNullWithFailureCode(final ValidationCodeType errorCode) {
        if (isNullOrEmpty()) {
            failWithCode(errorCode);
        }
        return this;
    }

    public void failWithCode(final ValidationCodeType errorCode) {
        final Errors error = new Errors(errorCode.getCategory(), errorCode.getCode(), errorCode.getMessage(), null);
        this.errorsList.add(error);
    }

    public void failWithCodeAndErrorParams(final ValidationCodeType errorCode, final List<ErrorParameter> errorParameters) {
        final Errors error = new Errors(errorCode.getCode(), errorCode.getCategory(), errorCode.getMessage(), errorParameters);
        this.errorsList.add(error);
    }

    public ValidatorBuilder validateFieldNotBlankAndLength(final int expectedLength) {
        if (this.value == null && this.ignoreNullValue) {
            return this;
        }

        if (this.value != null) {
            final String stringValue = this.value.toString();
            if (StringUtils.isBlank(stringValue) || stringValue.length() != expectedLength) {
                failWithCode(ValidationEnums.INVALID_LENGTH);
            }
        }
        return this;
    }

    public ValidatorBuilder validateFieldNotBlankAndLengthWithFailureCode(final int expectedLength, final ValidationCodeType errorCode) {
        if (this.value == null && this.ignoreNullValue) {
            return this;
        }

        if (isNullOrEmpty()) {
            failWithCode(errorCode);
        } else if (this.value != null) {
            final String stringValue = this.value.toString();
            if (StringUtils.isBlank(stringValue) || stringValue.length() != expectedLength) {
                failWithCode(errorCode);
            }
        }
        return this;
    }

    public ValidatorBuilder validateFieldNotBlankAndLengthWithFailureCodeAndErrorParams(final int expectedLength, final ValidationCodeType errorCode) {
        if (this.value == null && this.ignoreNullValue) {
            return this;
        }

        List<ErrorParameter> errorParam = List.of(new ErrorParameter(getParameter(), String.valueOf(expectedLength)));
        if (isNullOrEmpty()) {
            failWithCodeAndErrorParams(errorCode, errorParam);
        } else if (this.value != null) {
            final String stringValue = this.value.toString();
            if (StringUtils.isBlank(stringValue) || stringValue.length() != expectedLength) {
                failWithCodeAndErrorParams(errorCode, errorParam);
            }
        }
        return this;
    }

    public ValidatorBuilder validateListNotEmpty() {
        if (this.value == null && this.ignoreNullValue) {
            return this;
        }

        if (this.value != null && this.value instanceof List && ((List<?>) this.value).isEmpty()) {
            failWithCode(ValidationEnums.INVALID_LIST);
        }
        return this;
    }

    public ValidatorBuilder validateBigDecimalFieldNotNegative() {
        if (this.value == null && this.ignoreNullValue) {
            return this;
        }

        if (this.value != null) {
            try {
                final BigDecimal amount = new BigDecimal(this.value.toString());
                if (amount.compareTo(BigDecimal.ZERO) < 0) {
                    failWithCode(ValidationEnums.INVALID_NEGATIVE_FIELD);
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException("An error has occurred" + e.getMessage());
            }
        }
        return this;
    }

    public ValidatorBuilder validateBigDecimalFieldNotNegativeWithFailureCode(final ValidationCodeType errorCode) {
        if (this.value == null && this.ignoreNullValue) {
            return this;
        }

        if (this.value != null) {
            try {
                final BigDecimal amount = new BigDecimal(this.value.toString());
                if (amount.compareTo(BigDecimal.ZERO) < 0) {
                    failWithCode(errorCode);
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException("An error has occurred" + e.getMessage());
            }
        }
        return this;
    }

    public ValidatorBuilder validateFieldMaxLength(final int maxLength) {
        if (this.value == null && this.ignoreNullValue) {
            return this;
        }

        if (this.value != null) {
            final String stringValue = this.value.toString();
            if (stringValue.length() > maxLength) {
                failWithCode(ValidationEnums.INVALID_MAX_LENGTH);
            }
        }
        return this;
    }

    public ValidatorBuilder validateFieldMaxLengthWithFailureCode(final int maxLength, final ValidationCodeType errorCode) {
        if (this.value == null && this.ignoreNullValue) {
            return this;
        }

        if (isNullOrEmpty()) {
            failWithCode(errorCode);
        } else if (this.value != null) {
            final String stringValue = this.value.toString();
            if (stringValue.length() > maxLength) {
                failWithCode(errorCode);
            }
        }
        return this;
    }

    public ValidatorBuilder validateFieldMaxLengthWithFailureCodeAndErrorParams(final int maxLength, final ValidationCodeType errorCode) {
        if (this.value == null && this.ignoreNullValue) {
            return this;
        }

        List<ErrorParameter> errorParam = List.of(new ErrorParameter(getParameter(), String.valueOf(maxLength)));
        if (isNullOrEmpty()) {
            failWithCodeAndErrorParams(errorCode, errorParam);
        } else if (this.value != null) {
            final String stringValue = this.value.toString();
            if (stringValue.length() > maxLength) {
                failWithCodeAndErrorParams(errorCode, errorParam);
            }
        }
        return this;
    }
}
package org.mifos.connector.common.channel.dto;

import org.mifos.connector.common.gsma.dto.ErrorParameter;

import java.util.List;

public class Errors {
    public String errorCategory;
    public String errorCode;
    public String errorDescription;
    public List<org.mifos.connector.common.gsma.dto.ErrorParameter> errorParameters = null;

    public String getErrorCategory() {
        return errorCategory;
    }

    public void setErrorCategory(String errorCategory) {
        this.errorCategory = errorCategory;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public List<org.mifos.connector.common.gsma.dto.ErrorParameter> getErrorParameters() {
        return errorParameters;
    }

    public void setErrorParameters(List<ErrorParameter> errorParameters) {
        this.errorParameters = errorParameters;
    }

    @Override
    public String toString() {
        return "ErrorDTO{" + "errorCategory='" + errorCategory + '\'' + ", errorCode='" + errorCode + '\'' + ", errorDescription='"
                + errorDescription + '\''  + '\'' + ", errorParameters=" + errorParameters + '}';
    }
}

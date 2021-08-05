package org.mifos.connector.common.gsma.dto;

public class AuthErrorDTO {

    private  String errorCode;
    private String errorMessage;

    public AuthErrorDTO(){

    }

    public AuthErrorDTO(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

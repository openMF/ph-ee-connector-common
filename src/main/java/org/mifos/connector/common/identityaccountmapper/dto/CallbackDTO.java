package org.mifos.connector.common.identityaccountmapper.dto;

import java.util.List;

public class CallbackDTO {
    private String requestID;
    private String registerRequestID;
    private int numberFailedCases;
    private List<FailedCaseDTO> failedCases;

    public CallbackDTO(String requestID, String registerRequestID, int numberFailedCases, List<FailedCaseDTO> failedCases) {
        this.requestID = requestID;
        this.registerRequestID = registerRequestID;
        this.numberFailedCases = numberFailedCases;
        this.failedCases = failedCases;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getRegisterRequestID() {
        return registerRequestID;
    }

    public void setRegisterRequestID(String registerRequestID) {
        this.registerRequestID = registerRequestID;
    }

    public int getNumberFailedCases() {
        return numberFailedCases;
    }

    public void setNumberFailedCases(int numberFailedCases) {
        this.numberFailedCases = numberFailedCases;
    }

    public List<FailedCaseDTO> getFailedCases() {
        return failedCases;
    }

    public void setFailedCases(List<FailedCaseDTO> failedCases) {
        this.failedCases = failedCases;
    }
}

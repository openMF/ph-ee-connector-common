package org.mifos.connector.common.gsma.dto;

public class RequestStateDTO {

    private String notificationMethod;
    private String serverCorrelationId;
    private String status;
    private String pendingReason;
    private String objectReference;
    private String expiryTime;
    private String pollLimit;
    private ErrorDTO error;

    public String getNotificationMethod() {
        return notificationMethod;
    }

    public void setNotificationMethod(String notificationMethod) {
        this.notificationMethod = notificationMethod;
    }

    public String getServerCorrelationId() {
        return serverCorrelationId;
    }

    public void setServerCorrelationId(String serverCorrelationId) {
        this.serverCorrelationId = serverCorrelationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPendingReason() {
        return pendingReason;
    }

    public void setPendingReason(String pendingReason) {
        this.pendingReason = pendingReason;
    }

    public String getObjectReference() {
        return objectReference;
    }

    public void setObjectReference(String objectReference) {
        this.objectReference = objectReference;
    }

    public String getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getPollLimit() {
        return pollLimit;
    }

    public void setPollLimit(String pollLimit) {
        this.pollLimit = pollLimit;
    }

    public ErrorDTO getError() {
        return error;
    }

    public void setError(ErrorDTO error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "RequestStateDTO{" +
                "notificationMethod='" + notificationMethod + '\'' +
                ", serverCorrelationId='" + serverCorrelationId + '\'' +
                ", status='" + status + '\'' +
                ", pendingReason='" + pendingReason + '\'' +
                ", objectReference='" + objectReference + '\'' +
                ", expiryTime='" + expiryTime + '\'' +
                ", pollLimit='" + pollLimit + '\'' +
                ", error=" + error +
                '}';
    }
}

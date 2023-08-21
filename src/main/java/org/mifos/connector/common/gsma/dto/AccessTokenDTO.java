package org.mifos.connector.common.gsma.dto;

public class AccessTokenDTO {

    private String accessToken;
    private int expiresIn;

    public AccessTokenDTO() {

    }

    @Override
    public String toString() {
        return "AccessTokenDTO{" + "accessToken='" + accessToken + '\'' + ", expiresIn=" + expiresIn + '}';
    }

    public String getaccessToken() {
        return accessToken;
    }

    public void setaccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getexpiresIn() {
        return expiresIn;
    }

    public void setexpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public AccessTokenDTO(String accessToken, int expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }
}

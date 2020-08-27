package org.mifos.connector.common.gsma.dto;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AccessTokenStore {
    public String accessToken;
    public LocalDateTime expiresOn;

    public AccessTokenStore() {
        this.expiresOn = LocalDateTime.now();
        System.out.println("ACCESS TOKEN STORE CREATED!");
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public LocalDateTime getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(int expires_in) {
        this.expiresOn = LocalDateTime.now().plusSeconds(expires_in);
    }

    public boolean isValid(LocalDateTime dateTime) {
        if (dateTime.isBefore(expiresOn))
            return true;
        else
            return false;
    }

}
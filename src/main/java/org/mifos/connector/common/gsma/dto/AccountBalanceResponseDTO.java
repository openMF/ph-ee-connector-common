package org.mifos.connector.common.gsma.dto;

public class AccountBalanceResponseDTO {

    private String currentBalance;
    private String availableBalance;
    private String reservedBalance;
    private String unclearedBalance;
    private String currency;
    private String accountStatus;

    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getReservedBalance() {
        return reservedBalance;
    }

    public void setReservedBalance(String reservedBalance) {
        this.reservedBalance = reservedBalance;
    }

    public String getUnclearedBalance() {
        return unclearedBalance;
    }

    public void setUnclearedBalance(String unclearedBalance) {
        this.unclearedBalance = unclearedBalance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Override
    public String toString() {
        return "AccountBalanceResponseDTO{" + "currentBalance='" + currentBalance + '\'' + ", availableBalance='" + availableBalance + '\''
                + ", reservedBalance='" + reservedBalance + '\'' + ", unclearedBalance='" + unclearedBalance + '\'' + ", currency='"
                + currency + '\'' + ", accountStatus='" + accountStatus + '\'' + '}';
    }
}

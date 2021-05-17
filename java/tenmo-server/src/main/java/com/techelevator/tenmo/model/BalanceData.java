package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class BalanceData {


    private int accountID;
    private BigDecimal balance;

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


}

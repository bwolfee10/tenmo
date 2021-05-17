package com.techelevator.tenmo.models;

import java.math.BigDecimal;

public class Accounts {

    private Integer account_id;
    private Integer user_id;
    private BigDecimal balance;

    @Override
    public String toString() {
        return "Accounts{" +
                "account_id=" + account_id +
                ", user_id=" + user_id +
                ", balance=" + balance +
                '}';
    }

    public void setAccount_id(Integer account_id) {
        this.account_id = account_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getAccount_id() {
        return account_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}

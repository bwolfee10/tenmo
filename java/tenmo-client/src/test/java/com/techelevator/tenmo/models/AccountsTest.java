package com.techelevator.tenmo.models;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class AccountsTest {
    Accounts accounts = new Accounts();

    @Test
    public void test_set_account(){
        accounts.setAccount_id(1);
        Assert.assertEquals(1, (int) accounts.getAccount_id());
    }
    @Test
    public void test_set_user_id() {

        accounts.setUser_id(2);
        Assert.assertEquals(2,(int) accounts.getUser_id());
    }
    @Test
    public void test_get_account() {
        accounts.setBalance(new BigDecimal(600));
        Assert.assertEquals(new BigDecimal(600), accounts.getBalance());
    }

}
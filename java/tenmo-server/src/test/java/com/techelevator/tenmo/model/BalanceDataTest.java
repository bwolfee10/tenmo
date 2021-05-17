package com.techelevator.tenmo.model;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class BalanceDataTest {

    BalanceData balanceData = new BalanceData();


    @Test
    public void test_get_balance() {
        balanceData.setBalance(new BigDecimal(2000));
        Assert.assertEquals(new BigDecimal(2000), balanceData.getBalance());
    }

}
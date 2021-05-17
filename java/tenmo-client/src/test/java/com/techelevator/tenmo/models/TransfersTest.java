package com.techelevator.tenmo.models;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class TransfersTest {
    Transfers transferData = new Transfers();

    @Test
    public void transferId_sets() {
        transferData.setTransferId(2);
        Assert.assertEquals(2, transferData.getTransferId());
    }

    @Test
    public void transfer_type_set_test() {
        transferData.setTransfer_type(2);
        Assert.assertEquals(2, (int)transferData.getTransfer_type());
    }

    @Test
    public void transfer_status() {
        transferData.setTransfer_status(1);
        Assert.assertEquals(1, (int) transferData.getTransfer_status());
    }

    @Test
    public void get_account_from_test() {
        transferData.setAccount_from(2009);
        Assert.assertEquals(2009, (int) transferData.getAccount_from());
    }

    @Test
    public void get_account_to_test() {
        transferData.setAccount_to(2006);
        Assert.assertEquals(2006, (int) transferData.getAccount_to());
    }
    @Test
    public void get_amount_test() {
        transferData.setAmount(new BigDecimal("1000"));
        Assert.assertEquals(new BigDecimal("1000"), transferData.getAmount());
    }

}
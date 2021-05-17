package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.BalanceData;
import com.techelevator.tenmo.model.TransferData;

public interface AccountDAO {

    public BalanceData getBalanceGivenAnID(int id);


}

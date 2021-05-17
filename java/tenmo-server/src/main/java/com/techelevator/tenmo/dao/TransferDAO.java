package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.BalanceData;
import com.techelevator.tenmo.model.TransferData;

import java.util.List;

public interface TransferDAO {

    public void sendMoneyToAnotherAccount (TransferData transferData);
//
//    List<TransferData> getAllTransactionsUserSent(int fromAccount);
//
//    List<TransferData> getAllTransactionsUserGot(int toAccount);

    List<TransferData> getTransferById(int id);
}


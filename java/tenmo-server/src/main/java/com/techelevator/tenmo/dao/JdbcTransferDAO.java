package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.BalanceData;
import com.techelevator.tenmo.model.TransferData;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDAO implements TransferDAO {

    private JdbcTemplate template;

    public JdbcTransferDAO(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }


    @Override
    public void sendMoneyToAnotherAccount(TransferData transferData) {

        // selecting the account id and balance where the user id is
        String firstUser = "SELECT account_id,balance FROM accounts where user_id = ?";
        SqlRowSet toSomeone = template.queryForRowSet(firstUser, transferData.getAccount_to());

        int toAcount = 0;
        BigDecimal balance = new BigDecimal(0);
        while (toSomeone.next()) {
            toAcount = toSomeone.getInt("account_id");
            balance = toSomeone.getBigDecimal("balance");
        }

        //selecting the amount id and balance where the user id is

        String secondUser = "SELECT account_id,balance FROM accounts where user_id = ?";
        SqlRowSet fromSomeone = template.queryForRowSet(secondUser, transferData.getAccount_from());

        //need to change the id to an int and balance to big Decimal to work with
        int fromAccount = 0;
        BigDecimal balance2 = new BigDecimal(0);
        while(fromSomeone.next()) {
            fromAccount = fromSomeone.getInt("account_id");
            balance2 = fromSomeone.getBigDecimal("balance");
        }

        System.out.println("To Account" + toAcount);
        System.out.println("From Account" + fromAccount);

       String sql1 = "INSERT INTO transfers(transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES(2,2,?,?,?)";


        System.out.println(transferData.getTransfer_type());
        System.out.println(transferData.getTransfer_status());
        int transfer_type = transferData.getTransfer_type();
        int transfer_status = transferData.getTransfer_status();
        int to_someone = transferData.getAccount_to();
        int from_someone = transferData.getAccount_from();
        BigDecimal amount = transferData.getAmount();

        //insert statement - adding in the from and to account information we created
        template.update(sql1, fromAccount, toAcount, amount);


        //subtracting from our balance
        sql1 = "UPDATE accounts SET balance =  ? WHERE account_id = ?";
        template.update(sql1, balance.subtract(amount),fromAccount);


        //adding to our balance
        sql1 = "UPDATE accounts SET balance =  ? WHERE account_id = ?";
        template.update(sql1, balance2.add(amount), toAcount);

    }

    //showing everything from the users account
//    @Override
//    public List<TransferData> getAllTransactionsUserSent(int fromAccount) {
//
//        List<TransferData> transfers = new ArrayList<>();
//
//        String sqlSent = "SELECT * FROM transfers WHERE account_from = ?";
//        SqlRowSet rowSet = template.queryForRowSet(sqlSent, fromAccount);
//
//        while (rowSet.next()) {
//            TransferData sent = mapRowToTransfer(rowSet);
//            transfers.add(sent);
//        }
//
//        return transfers;
//    }

    //showing the transfers the user received from other users
//    @Override
//    public List<TransferData> getAllTransactionsUserGot(int toAccount) {
//
//        List<TransferData> transfers = new ArrayList<>();
//
//        String sqlReceived = "SELECT * FROM transfers WHERE account_to = ?";
//        SqlRowSet rowSet = template.queryForRowSet(sqlReceived, toAccount);
//
//        while (rowSet.next()) {
//            TransferData received = mapRowToTransfer(rowSet);
//            transfers.add(received);
//        }
//        return transfers;
//    }


    @Override
    public  List<TransferData> getTransferById(int id) {

        //pulling in the transferdata.java object
        List<TransferData> transfer = new ArrayList<>();
        TransferData transferData = new TransferData();

        String firstUser = "SELECT account_id FROM accounts where user_id = ?";
        SqlRowSet toSomeone = template.queryForRowSet(firstUser, id);

        System.out.println(transferData.getAccount_to());

        int account = 0;
        while (toSomeone.next()) {
            account = toSomeone.getInt("account_id");
        }
        System.out.println(account);

        // before we run the query below run another query that translates the id given
        // to an account_id: SELECT

        System.out.println("The id we are searching for: " + id);

        String sql = "SELECT transfer_id,transfer_type_id, transfer_status_id,account_from,account_to, amount " +
                "FROM transfers WHERE account_from = ? or account_to = ? ";

        SqlRowSet rowSet = template.queryForRowSet(sql, account, account);

        while (rowSet.next()) {
            TransferData transferData1 = mapRowToTransfer(rowSet);
            transfer.add(transferData1);
        }
        return transfer;
    }


    public TransferData mapRowToTransfer(SqlRowSet results) {
        //setting the transferdata object
        TransferData transferData = new TransferData();
        transferData.setTransferId(results.getInt("transfer_id"));
        transferData.setTransfer_type(results.getInt("transfer_type_id"));
        transferData.setTransfer_status(results.getInt("transfer_status_id"));
        transferData.setAccount_from(results.getInt("account_from"));
        transferData.setAccount_to(results.getInt("account_to"));
        transferData.setAmount(results.getBigDecimal("amount"));
        return transferData;
    }
}
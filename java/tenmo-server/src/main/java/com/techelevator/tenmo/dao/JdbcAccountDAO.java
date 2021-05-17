package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.BalanceData;
import com.techelevator.tenmo.model.TransferData;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;

@Component
public class JdbcAccountDAO implements AccountDAO {

    private JdbcTemplate template;

    public JdbcAccountDAO(DataSource datasource) {
        this.template = new JdbcTemplate(datasource);
    }


    public BalanceData getBalanceGivenAnID(int id) {
        String sql = "SELECT account_id, balance from accounts where user_id = ?";
        SqlRowSet rowSet = template.queryForRowSet(sql, id);

        BalanceData balanceData = new BalanceData();


        if (rowSet.next()) {
            String balance = rowSet.getString("balance");
            BigDecimal balanceBD = new BigDecimal(balance);
            int account_id = rowSet.getInt("account_id");

            balanceData.setBalance(balanceBD);
            balanceData.setAccountID(account_id);
        }


        return balanceData;
    }


}







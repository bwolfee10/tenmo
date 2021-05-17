package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.BalanceData;
import com.techelevator.tenmo.model.TransferData;
import com.techelevator.tenmo.model.User;
import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class JdbcTransferDAOTest {

    private static SingleConnectionDataSource dataSource;

    private AccountDAO accountDAO;
    private UserDAO userDAO;
    private TransferDAO transferDAO;
    private BalanceData balanceData;
    private static final String TEST_USER = "joe";

    @BeforeClass
    public static void setup() {
        dataSource = new SingleConnectionDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/tenmo");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        dataSource.setAutoCommit(false);
    }

    @AfterClass
    public static void cleanup() {
        dataSource.destroy();
    }

    @Before
    public void setupBeforeTest() {
        // do some kind of sql setup.
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        userDAO = new JdbcUserDAO(jdbcTemplate);
        transferDAO = new JdbcTransferDAO(dataSource);
        accountDAO = new JdbcAccountDAO(dataSource);
        // run your setup queries
    }

    @After
    public void doAfterEveryTest() throws Exception {
        dataSource.getConnection().rollback();
    }

    @Test
    public void check_users_balance() {
        userDAO.create("sgf", "pol");

        int user = userDAO.findIdByUsername("sgf");

        BalanceData balanceData = accountDAO.getBalanceGivenAnID(user);

        Assert.assertEquals(new BigDecimal("1000.00"), balanceData.getBalance());
    }


    @Test
    public void get_users() {

        User user = new User();

        userDAO.create("hey", "ho");

        User test = userDAO.findByUsername("hey");

        Assert.assertEquals("hey", user.getUsername());


//        boolean test3 = false;
//
//        for(User user : test) {
//            if(user.getUsername().equals("hey")) {
//                test3=true;
//            }
//            Assert.assertTrue(test3);
    }

//        int testSize = test.size();
//
//        userDAO.create("hey", "ho");
//
//        List<User> change = userDAO.findAll();
//
//        int test2 = change.size();
//
//        System.out.println(test);
//        System.out.println(test2);
//        Assert.assertEquals((testSize+1), test2);


    @Test
    public void send_Money_Test() {
        //create accounts
        userDAO.create("Lola", "kitty");
        userDAO.create("Nick", "abc123");

        //store username as an into to put in BalanceData
        int senderUserId = userDAO.findIdByUsername("Lola");
        int recipientUserId = userDAO.findIdByUsername("Nick");

        BalanceData senderBalanceData = accountDAO.getBalanceGivenAnID(senderUserId);
        BalanceData recipientBalanceData = accountDAO.getBalanceGivenAnID(recipientUserId);

        //transferData Object
        TransferData transfer = new TransferData();

        int set_type = 2;
        int transferStatus = 1;
        BigDecimal amountToSend = new BigDecimal("50.00");

        transfer.setTransfer_type(set_type);
        transfer.setTransfer_status(transferStatus);
        transfer.setAccount_from(senderUserId);
        transfer.setAccount_to(recipientUserId);
        transfer.setAmount(amountToSend);

        //call method
        transferDAO.sendMoneyToAnotherAccount(transfer);

        //assert
        //actual
        BalanceData updatedSenderBalance = accountDAO.getBalanceGivenAnID(senderUserId);
        BalanceData updatedRecipientBalance = accountDAO.getBalanceGivenAnID(recipientUserId);

        //expected
        BigDecimal SenderNewBalanceAfterSending = senderBalanceData.getBalance().subtract(amountToSend);
        BigDecimal RecipientBalanceAfter = recipientBalanceData.getBalance().add(amountToSend);

        //checking to see
        Assert.assertEquals(SenderNewBalanceAfterSending, updatedSenderBalance.getBalance());
        Assert.assertEquals(RecipientBalanceAfter, updatedRecipientBalance.getBalance());

    }


    @Test
    public void getTransferById() {

        List<TransferData> transferList = transferDAO.getTransferById(1001);

        System.out.println(transferList.size());

        assertEquals(3006, transferList.get(0).getTransferId());
        System.out.println(transferList.get(0).getTransferId());

    }
}
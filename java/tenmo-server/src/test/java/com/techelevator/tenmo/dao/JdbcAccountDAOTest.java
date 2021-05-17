package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;
import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.List;

import static org.junit.Assert.*;

public class JdbcAccountDAOTest {



    private static SingleConnectionDataSource dataSource;


    private AccountDAO accountDAO;
    private UserDAO userDAO;


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
    public void setUpBeforeTest() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        userDAO = new JdbcUserDAO(jdbcTemplate);
        }


    @After
    public void doAfterTest() throws Exception {
        dataSource.getConnection().rollback();
    }


    @Test
    public void testUser() {

        int user = userDAO.findIdByUsername("hey");
        Assert.assertEquals(1002, user);
    }

}
package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDAO;

import com.techelevator.tenmo.dao.TransferDAO;

import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.BalanceData;
import com.techelevator.tenmo.model.TransferData;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;


import org.springframework.web.bind.annotation.*;


import javax.naming.InsufficientResourcesException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class ApplicationController {

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    TransferDAO transferDAO;


    @RequestMapping(path = "/get-balance", method = RequestMethod.GET)
    public BalanceData processBalanceRequests(Principal principal) {
        System.out.println("Requesting Balance");
        System.out.println(principal.getName());

        int correspondingUserId = userDAO.findIdByUsername(principal.getName());

        BalanceData balanceObject = accountDAO.getBalanceGivenAnID(correspondingUserId);
        System.out.println(balanceObject.getBalance());

        return balanceObject;
    }

    //    @PreAuthorize("hasRole('User')")
    @PreAuthorize("permitAll")
    @RequestMapping(path = "/all-users/{id}/transfers", method = RequestMethod.GET)
    public List<TransferData> getReceivedAndSentRequestsFromUser(@PathVariable int id, Principal principal) {

        List<TransferData> transfers = new ArrayList<>();
        transfers = transferDAO.getTransferById(id);


        return transfers;
    }


    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public void transferBalance(@RequestBody TransferData transferData) {


        if (accountDAO.getBalanceGivenAnID(transferData.getAccount_from()).getBalance().compareTo(transferData.getAmount()) != -1) {

            System.out.println("hi");
            System.out.println(transferData.getTransfer_type());
            System.out.println(transferData.getAmount());
            System.out.println("---------------------------");
            System.out.println(transferData.getAccount_from());
            System.out.println(transferData.getAccount_to());
            transferDAO.sendMoneyToAnotherAccount(transferData);
        } else {
            System.out.println("You don't have enough funds to transfer.");
        }
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "/all-users", method = RequestMethod.GET)
    public List<User> listUsers() {

        return userDAO.findAll();
    }


}




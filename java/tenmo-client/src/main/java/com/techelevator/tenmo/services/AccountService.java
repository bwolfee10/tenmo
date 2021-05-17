package com.techelevator.tenmo.services;

import com.techelevator.tenmo.models.Accounts;
import com.techelevator.tenmo.models.AuthenticatedUser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.AccountException;


public class AccountService {

    private static String API_BASE_URL = "http://localhost:8080/";
    public RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser = new AuthenticatedUser();

    public AccountService(String base_url) {
        API_BASE_URL = base_url;
    }

    private HttpEntity<Accounts> makeAuthEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Accounts> entity = new HttpEntity<>(headers);
        return entity;
    }

    public Accounts getBalance(String token) {


            Accounts accounts = restTemplate.exchange(API_BASE_URL + "/get-balance", HttpMethod.GET, makeAuthEntity(token), Accounts.class).getBody();

            System.out.println("This is your current balance " + accounts.getBalance());
            return accounts;

    }
}

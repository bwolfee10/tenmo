package com.techelevator.tenmo.services;


import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfers;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferService {

    private static String API_BASE_URL = "http://localhost:8080/";
    public RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser = new AuthenticatedUser();
    private static final String AUTH_TOKEN = "";


    public TransferService(String base_url) {
        API_BASE_URL = base_url;
    }

    private HttpEntity<Transfers> makeAuthEntity(String Token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(Token);
        HttpEntity<Transfers> entity = new HttpEntity<>(headers);
        return entity;
    }

    public Transfers sendTransfer(Transfers transfers, AuthenticatedUser currentUser) throws TransferServiceException {

        HttpEntity entity = makeTransfer(transfers, currentUser.getToken());
        try {
            restTemplate.exchange(API_BASE_URL + "/transfer", HttpMethod.POST, entity, Transfers.class);
            System.out.println("Account From " + " " + transfers.getAccount_from() + " " + "Transferring to Account " + " " + transfers.getAccount_to() + "Amount to be transferred: " + transfers.getAmount());
        } catch (RestClientResponseException ex) {
            throw new TransferServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }
        return transfers;
    }

    private HttpEntity<Transfers> makeTransfer(Transfers transfers, String Token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(Token);
        HttpEntity<Transfers> entity = new HttpEntity<>(transfers, headers);
        return entity;

    }

    public Transfers[] getTransferInfo(AuthenticatedUser currentUser) throws TransferServiceException {
        Transfers[] transfers = null;
        try {
            transfers = restTemplate.exchange(API_BASE_URL + "all-users/" + currentUser.getUser().getId() +
                    "/transfers", HttpMethod.GET, makeAuthEntity2(), Transfers[].class).getBody();
            System.out.println(transfers.length);
        } catch (RestClientResponseException e) {
            throw new TransferServiceException(e.getRawStatusCode() + ":" + e.getResponseBodyAsString());
        }
        return transfers;
    }

//    public User[] listUsers(String Token, AuthenticatedUser currentUser) {
//        User[] users = restTemplate.exchange(API_BASE_URL + "/all-users", HttpMethod.GET, makeAuthEntity(Token), User[].class ).getBody();
//        return users;
//    }

    private HttpEntity<Transfers> makeAuthEntity2() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity<Transfers> entity = new HttpEntity<>(headers);
        return entity;
    }

}

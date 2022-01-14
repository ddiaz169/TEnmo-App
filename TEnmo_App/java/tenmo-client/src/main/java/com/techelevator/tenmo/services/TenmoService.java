package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;


public class TenmoService {

   private RestTemplate restTemplate = new RestTemplate();
   private String authToken = null;
   private final String API_BASE_URL = "http://localhost:8080/";

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public BigDecimal retrieveBalance(AuthenticatedUser currentUser) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        Balance balance = restTemplate.exchange(API_BASE_URL + "/balance", HttpMethod.GET, entity, Balance.class).getBody();

        return balance.getBalance();
    }

    public Account getAccountByUserId(AuthenticatedUser authenticatedUser, int userId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        Account account = null;
        try {
            account = restTemplate.exchange(API_BASE_URL + "account/user/" + userId, HttpMethod.GET, entity, Account.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("Error: " + e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println("Error" + e.getMessage());
        }
        return account;
    }

    public Account getAccountByAccountId(AuthenticatedUser currentUser, int accountId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        Account account = null;
        try {
            account = restTemplate.exchange(API_BASE_URL + "account/" + accountId, HttpMethod.GET, entity, Account.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("Error: " + e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println("Error" + e.getMessage());
        }
        return account;
    }

    public void createTransfer(AuthenticatedUser currentUser, Transfer transfer) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);

        try {
            restTemplate.exchange(API_BASE_URL + "transfers/", HttpMethod.POST, entity, Transfer.class);
        } catch (RestClientResponseException e) {
            System.out.println("Insufficient funds.");

        } catch (ResourceAccessException e) {
            System.out.println("Insufficient funds.");
        }
    }

    public Transfer getTransferStatus (AuthenticatedUser authenticatedUser, String description) {
        Transfer transferStatus = null;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Transfer> entity = new HttpEntity<>(transferStatus, headers);

        try {
            String url = API_BASE_URL + "transferstatus/filter?description=" + description; {
            transferStatus = restTemplate.exchange(url, HttpMethod.GET, entity, Transfer.class).getBody();
            }
        } catch (RestClientResponseException e) {
            System.out.println("Could not complete request. Code " + e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println("Could not complete request due to server network issue. Please try again.");
        }
        return transferStatus;
    }

    public Transfer getTransferType (AuthenticatedUser authenticatedUser, String description) {
        Transfer transferType = null;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Transfer> entity = new HttpEntity<>(transferType, headers);

        try {
            String url = API_BASE_URL + "transfertype/filter?description=" + description; {
                transferType = restTemplate.exchange(url, HttpMethod.GET, entity, Transfer.class).getBody();
            }
        } catch (RestClientResponseException e) {
            System.out.println("Could not complete request. Code " + e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println("Could not complete request due to server network issue. Please try again.");
        }
        return transferType;
    }

    public Transfer[] getTransfersFromUserId(AuthenticatedUser currentUser, int userId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        Transfer[] transfers = null;
        try {
            transfers = restTemplate.exchange(API_BASE_URL + "transfers/user/" + userId, HttpMethod.GET, entity, Transfer[].class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("");
        } catch (ResourceAccessException e) {
            System.out.println("");
        }
        return transfers;
    }

    public Transfer getTransfersByTransferId (int id) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        Transfer transfers = null;
        try{
            transfers = restTemplate.exchange(API_BASE_URL + "transfers/" + id, HttpMethod.GET, entity, Transfer.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("");
        } catch (ResourceAccessException e) {
            System.out.println("");
        }
        return transfers;
    }

    public Transfer[] getAllTransfers(AuthenticatedUser authenticatedUser) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        Transfer[] transfers = new Transfer[0];
        try{
            transfers = restTemplate.exchange(API_BASE_URL + "transfers/", HttpMethod.GET, entity, Transfer[].class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("");
        } catch (ResourceAccessException e) {
            System.out.println("");
        }
        return transfers;
    }

    public TransferStatus getTransferStatusById(AuthenticatedUser authenticatedUser, int transferStatusId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        User[] users = null;

        TransferStatus transferStatus = null;
        try{
            String url = API_BASE_URL + "transferstatus/" + transferStatusId;
            transferStatus = restTemplate.exchange(url, HttpMethod.GET, entity, TransferStatus.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("");
        } catch (ResourceAccessException e) {
            System.out.println("");
        }
        return transferStatus;
    }


    public TransferStatus getTransferStatusByDescription(AuthenticatedUser authenticatedUser, String description) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        User[] users = null;

        TransferStatus transferStatus = null;
        try {
            String url = API_BASE_URL + "transferstatus/filter?description =" + description;
            transferStatus = restTemplate.exchange(url, HttpMethod.GET, entity, TransferStatus.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("");
        } catch (ResourceAccessException e) {
            System.out.println("");
        }
        return transferStatus;
    }

    public TransferType getTransferTypeById(AuthenticatedUser authenticatedUser, int transferTypeId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        TransferType transferType = null;
        try {
            String url = API_BASE_URL + "transfertype/" + transferTypeId;
            transferType = restTemplate.exchange(url, HttpMethod.GET, entity, TransferType.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("");
        } catch (ResourceAccessException e) {
            System.out.println("");
        }
        return transferType;
    }

    public TransferType getTransferTypeByDescription(AuthenticatedUser authenticatedUser, String description) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        TransferType transferType = null;
        try {
            String url = API_BASE_URL + "transfertype/filter?description=" + description;
            transferType = restTemplate.exchange(url, HttpMethod.GET, entity, TransferType.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("");
        } catch (ResourceAccessException e) {
            System.out.println("");
        }
        return transferType;
    }

    public User[] getAllUsers(AuthenticatedUser currentUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        User[] users = null;
        try {
            users = restTemplate.exchange(API_BASE_URL + "users", HttpMethod.GET, entity, User[].class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("");
        } catch (ResourceAccessException e) {
            System.out.println("");
        }
        return users;
    }

    public User getUserByUserId(AuthenticatedUser currentUser, int userId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        User users = null;
        try {
            users = restTemplate.exchange(API_BASE_URL + "users/" + userId, HttpMethod.GET, entity, User.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("");
        } catch (ResourceAccessException e) {
            System.out.println("");
        }
        return users;
        }
    }

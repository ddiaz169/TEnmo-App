package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {

    BigDecimal retrieveBalance(int userId);
    Account getAccountByAccountId(int accountId);
    Account getAccountByUserId(int userId);
    void updateAccount(Account updatedAccount);
}

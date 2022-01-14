package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal retrieveBalance(int userId) {

        String sql = "SELECT balance FROM accounts WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

        BigDecimal balance = null;

        if (results.next()) {
            balance = results.getBigDecimal("balance");
        }

        return balance;
    }

    @Override
    public Account getAccountByAccountId(int accountId) {
        String sql = "SELECT account_id, user_id, balance FROM accounts WHERE account_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, accountId);
        Account account = null;
        if (rowSet.next()) {
            account = mapToRowSet(rowSet);
        }
        return account;
    }

    @Override
    public Account getAccountByUserId(int userId) {
        String sql = "SELECT account_id, user_id, balance FROM accounts WHERE user_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        Account account = null;
        if (rowSet.next()) {
            account = mapToRowSet(rowSet);
        }
        return account;
    }

    @Override
    public void updateAccount(Account updatedAccount) {
        String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
        jdbcTemplate.update(sql,updatedAccount.getBalance().getBalance(), updatedAccount.getAccountId());
    }

    private Account mapToRowSet(SqlRowSet rowSet) {
        int accountId = rowSet.getInt("account_id");
        int userId = rowSet.getInt("user_id");
        Balance balance = new Balance();

        Account account = new Account(accountId, userId, balance);

        return account;
    }

}

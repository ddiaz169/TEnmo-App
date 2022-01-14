package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao (JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate;}

    @Override
    public void createTransfer(Transfer transfer) {

        // create transfer
        String sql = "INSERT INTO transfers (account_from, account_to, transfer_type_id, transfer_status_id, amount) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getTransferTypeId(), transfer.getTransferStatusId(), transfer.getTransferAmount());
    }

    @Override
    public void updateTransfer(Transfer transfer) {
        String sql = "UPDATE transfers SET transfer_id = ? " +
                "WHERE transfer_id = ?";
        jdbcTemplate.update(sql, transfer.getTransferStatusId(), transfer.getTransferId());
    }

    @Override
    public Transfer getTransferByTransferId(int transferId) {
        String sql = "SELECT transfer_id, account_from, account_to, transfer_type_id, transfer_status_id, amount" +
                " FROM transfers WHERE transfer_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, transferId);
        Transfer transfer = null;
        if (rowSet.next()){

            transfer = mapToRowSet(rowSet);
        }
        return transfer;
    }

    @Override
    public List<Transfer> getTransferByUserId(int userId) {
        String sql = "SELECT transfer_id, account_from, account_to, transfer_type_id, transfer_status_id, amount" +
                " FROM transfers JOIN accounts ON accounts.account_id = transfers.account_from OR accounts.account_id = transfers.account_to " +
                "WHERE user_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        List<Transfer> transfers = new ArrayList<>();

        while (rowSet.next()) {
            transfers.add(mapToRowSet(rowSet));
        }
        return transfers;
    }

    public List<Transfer> getAllTransfers() {
        String sql = "SELECT transfer_id, account_from, account_to, transfer_type_id, transfer_status_id, amount" +
                " FROM transfers";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
        List<Transfer> transfers = new ArrayList<>();

        while (rowSet.next()) {
            transfers.add(mapToRowSet(rowSet));
        }
        return transfers;
    }

    private Transfer mapToRowSet (SqlRowSet rowSet) {

        int transferId = rowSet.getInt("transfer_id");
        int transferTypeId = rowSet.getInt("transfer_type_id");
        int transferStatusId = rowSet.getInt("transfer_status_id");
        int accountFrom = rowSet.getInt("account_from");
        int accountTo = rowSet.getInt("account_to");
        BigDecimal transferAmount = rowSet.getBigDecimal("amount");

        return new Transfer( transferId,  accountFrom,  accountTo,  transferTypeId,  transferStatusId,  transferAmount);
    }

}

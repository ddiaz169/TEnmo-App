package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JdbcTransferStatusDao implements TransferStatusDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferStatusDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TransferStatus getTransferStatusDescription(String description) {
        String sql = "SELECT transfer_status_id, transfer_status_desc FROM transfer_statuses WHERE transfer_status_desc = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, description);
        TransferStatus transferStatus = null;
        if (rowSet.next()) {
            int transferStatusId = rowSet.getInt("transfer_status_id");
            String transferStatusDescription = rowSet.getString("transfer_status_desc");
            transferStatus = new TransferStatus(transferStatusId, transferStatusDescription);
        }
        return transferStatus;
    }

    @Override
    public TransferStatus getTransferStatusId(int transferStatusId) {
        String sql = "SELECT transfer_status_id, transfer_status_desc FROM transfer_statuses WHERE transfer_status_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, transferStatusId);
        TransferStatus transferStatus = null;
        if (rowSet.next()) {
            int transferStatusId2 = rowSet.getInt("transfer_status_id");
            String transferStatusDescription2 = rowSet.getString("transfer_status_desc");
            transferStatus = new TransferStatus(transferStatusId2, transferStatusDescription2);
        }
        return transferStatus;
    }
}

package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

@Component
public class JdbcTransferTypeDao implements TransferTypeDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferTypeDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TransferType getTransferTypeDescription(String description) {
        String sql = "SELECT transfer_type_id, transfer_type_desc FROM transfer_types WHERE transfer_type_desc = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, description);
        TransferType transferType = null;
        if (rowSet.next()) {
            int transferTypeId = rowSet.getInt("transfer_type_id");
            String transferTypeDescription = rowSet.getString("transfer_type_desc");

            transferType = new TransferType(transferTypeId, transferTypeDescription);
        }
        return transferType;
    }

    @Override
    public TransferType getTransferTypeId(int transferId) {
        String sql = "SELECT transfer_type_id, transfer_type_desc FROM transfer_types WHERE transfer_type_id =?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, transferId);
        TransferType transferType = null;
        if (rowSet.next()) {
            int transferTypeId = rowSet.getInt("transfer_type_id");
            String transferTypeDescription = rowSet.getString("transfer_type_desc");

            transferType = new TransferType(transferTypeId, transferTypeDescription);
        }
        return transferType;
    }
}

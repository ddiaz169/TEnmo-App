package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    void createTransfer(Transfer transfer);

    Transfer getTransferByTransferId(int transferId);

    List<Transfer> getTransferByUserId(int userId);

    List<Transfer> getAllTransfers();

    void updateTransfer(Transfer transfer);
}

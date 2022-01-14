package com.techelevator.tenmo.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class TransferStatusTest {

    @Test
    public void getTransferStatusId() {

            TransferStatus testTransferStatus1 = new TransferStatus(2, "Send");
            TransferStatus testTransferStatus2 = new TransferStatus(1, "Request");

            int expected1 = 2;
            int actual1 = testTransferStatus1.getTransferStatusId();
            int expected2 = 1;
            int actual2 = testTransferStatus2.getTransferStatusId();

            assertEquals(expected1, actual1);
            assertEquals(expected2, actual2);
    }


    @Test
    public void getTransferStatusDescription() {

        TransferStatus testTransferStatus1 = new TransferStatus(2, "Send");
        TransferStatus testTransferStatus2 = new TransferStatus(1, "Request");

        String expected1 = "Send";
        String actual1 = testTransferStatus1.getTransferStatusDescription();
        String expected2 = "Request";
        String actual2 = testTransferStatus2.getTransferStatusDescription();

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }
}
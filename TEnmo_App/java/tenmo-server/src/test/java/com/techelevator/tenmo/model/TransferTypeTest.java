package com.techelevator.tenmo.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class TransferTypeTest {

    @Test
    public void getTransferTypeDescription() {

        TransferType testTransferType1 = new TransferType(2, "Send");
        TransferType testTransferType2 = new TransferType(1, "Request");

        String expected1 = "Send";
        String actual1 = testTransferType1.getTransferTypeDescription();
        String expected2 = "Request";
        String actual2 = testTransferType2.getTransferTypeDescription();

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    public void getTransferTypeId() {

        TransferType testTransferType1 = new TransferType(2, "Send");
        TransferType testTransferType2 = new TransferType(1, "Request");

        int expected1 = 2;
        int actual1 = testTransferType1.getTransferTypeId();
        int expected2 = 1;
        int actual2 = testTransferType2.getTransferTypeId();

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }
}
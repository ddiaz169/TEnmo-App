package com.techelevator.tenmo.model;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class TransferTest {

    @Test
    public void getTransferId() {

        Transfer testTransfer1 = new Transfer(1234, 2222, 3333, 2, 1, new BigDecimal("300.00"));
        Transfer testTransfer2 = new Transfer(9876, 9999, 1111, 1, 2, new BigDecimal("10.00"));

        int expected1 = 1234;
        int actual1 = testTransfer1.getTransferId();
        int expected2 = 9876;
        int actual2 = testTransfer2.getTransferId();

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);

    }

    @Test
    public void getAccountFrom() {

        Transfer testTransferAccountFrom1 = new Transfer(1234, 2222, 3333, 2, 1, new BigDecimal("300.00"));
        Transfer testTransferAccountFrom2 = new Transfer(9876, 9999, 1111, 1, 2, new BigDecimal("10.00"));

        int expected1 = 2222;
        int actual1 = testTransferAccountFrom1.getAccountFrom();
        int expected2 = 9999;
        int actual2 = testTransferAccountFrom2.getAccountFrom();

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);

    }

    @Test
    public void getAccountTo() {

        Transfer testTransferAccountTo1 = new Transfer(1234, 2222, 3333, 2, 1, new BigDecimal("300.00"));
        Transfer testTransferAccountTo2 = new Transfer(9876, 9999, 1111, 1, 2, new BigDecimal("10.00"));

        int expected1 = 3333;
        int actual1 = testTransferAccountTo1.getAccountTo();
        int expected2 = 1111;
        int actual2 = testTransferAccountTo2.getAccountTo();

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);

    }

    @Test
    public void getTransferTypeId() {

        Transfer testTransferTypeId1 = new Transfer(1234, 2222, 3333, 2, 1, new BigDecimal("300.00"));
        Transfer testTransferTypeId2 = new Transfer(9876, 9999, 1111, 1, 2, new BigDecimal("10.00"));

        int expected1 = 2;
        int actual1 = testTransferTypeId1.getTransferTypeId();
        int expected2 = 1;
        int actual2 = testTransferTypeId2.getTransferTypeId();

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);

    }

    @Test
    public void getTransferStatusId() {

        Transfer testTransferStatusId1 = new Transfer(1234, 2222, 3333, 2, 1, new BigDecimal("300.00"));
        Transfer testTransferStatusId2 = new Transfer(9876, 9999, 1111, 1, 2, new BigDecimal("10.00"));

        int expected1 = 1;
        int actual1 = testTransferStatusId1.getTransferStatusId();
        int expected2 = 2;
        int actual2 = testTransferStatusId2.getTransferStatusId();

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);

    }

    @Test
    public void getTransferAmount() {

        Transfer testTransferAmount1 = new Transfer(1234, 2222, 3333, 2, 1, new BigDecimal("300.00"));
        Transfer testTransferAmount2 = new Transfer(9876, 9999, 1111, 1, 2, new BigDecimal("10.00"));

        BigDecimal expected1 = new BigDecimal("300.00");
        BigDecimal actual1 = testTransferAmount1.getTransferAmount();
        BigDecimal expected2 = new BigDecimal("10.00");
        BigDecimal actual2 = testTransferAmount2.getTransferAmount();

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);

    }
}
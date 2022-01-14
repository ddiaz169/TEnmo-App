package com.techelevator.tenmo.model;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class AccountTest {

    @Test
    public void getAccountId() {


        Balance testBalance1 = new Balance();
        testBalance1.setBalance(new BigDecimal("500.00"));
        Account testAccount1 = new Account(2001, 1001, testBalance1);

        Balance testBalance2 = new Balance();
        testBalance2.setBalance(new BigDecimal("777.00"));
        Account testAccount2 = new Account(2003, 1003, testBalance2);

        int expected1 = 2001;
        int actual1 = testAccount1.getAccountId();
        int expected2 = 2003;
        int actual2 = testAccount2.getAccountId();

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);


    }

    @Test
    public void getUserId() {

        Balance testBalance1 = new Balance();
        testBalance1.setBalance(new BigDecimal("888.00"));
        Account testAccount1 = new Account(2001, 1001, testBalance1);

        Balance testBalance2 = new Balance();
        testBalance2.setBalance(new BigDecimal("1250.00"));
        Account testAccount2 = new Account(2003, 1003, testBalance2);

        int expected1 = 1001;
        int actual1 = testAccount1.getUserId();
        int expected2 = 1003;
        int actual2 = testAccount2.getUserId();

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

}
package com.techelevator.tenmo.model;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void getId() {

        User testUser1 = new User(1234L, "testUser1", "password1", "true");
        User testUser2 = new User(9876L, "testUser2", "password2", "true");

        Long expected1 = 1234L;
        Long actual1 = testUser1.getId();
        Long expected2 = 9876L;
        Long actual2 = testUser2.getId();

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);

    }

    @Test
    public void getUsername() {

        User testUser1 = new User(1234L, "testUser1", "password1", "true");
        User testUser2 = new User(9876L, "testUser2", "password2", "true");

        String expected1 = "testUser1";
        String actual1 = testUser1.getUsername();
        String expected2 = "testUser2";
        String actual2 = testUser2.getUsername();

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);

    }
}
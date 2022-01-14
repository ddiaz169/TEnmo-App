package com.techelevator.tenmo.exceptions;

public class InvalidUserChoiceException extends Exception{
    public InvalidUserChoiceException() {
        super("Invalid User Id, please choose a different user.");
    }
}

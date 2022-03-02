package com.grupo5.huiapi.exceptions;

public class UserNotFoundException extends Exception{
    private static final String defaultMessage = "Couln't find a user with this id.";

    public UserNotFoundException() {
        super(defaultMessage);
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}

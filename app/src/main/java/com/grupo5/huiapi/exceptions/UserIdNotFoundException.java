package com.grupo5.huiapi.exceptions;

public class UserIdNotFoundException extends Exception{
    private static final String defaultMessage = "Couln't find a user with this id.";

    public UserIdNotFoundException() {
        super(defaultMessage);
    }

    public UserIdNotFoundException(String message) {
        super(message);
    }
}

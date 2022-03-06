package com.grupo5.huiapi.exceptions;

public class EventNotFoundException extends Exception{
    private static final String defaultMessage = "Couln't find an event with this id.";

    public EventNotFoundException() {
        super(defaultMessage);
    }

    public EventNotFoundException(String message) {
        super(message);
    }
}

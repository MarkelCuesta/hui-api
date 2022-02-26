package com.grupo5.huiapi.exceptions;

public class RequiredValuesMissingException  extends Exception {
    private static final String defaultMessage = "Some required values are missing";
    public RequiredValuesMissingException() {
        super(defaultMessage);
    }
    public RequiredValuesMissingException(String message) {
        super(message);
    }
}

package com.grupo5.huiapi.exceptions;

public class RequiredValuesMissingException  extends Exception {
    private String message;
    private static String defaultMessage = "Some required values are missing";

    public RequiredValuesMissingException(String fieldsMessage) {
        super(defaultMessage + ": (" + fieldsMessage + ")");
    }
}

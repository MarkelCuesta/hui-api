package com.grupo5.huiapi.exceptions;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String entityName) {
        super("Couldn't find the " + entityName + " with this id.");
    }
}

package com.grupo5.huiapi.exceptions;

public class CategoryNotFoundException extends Exception {
    public final static String defaultMessage = "Couldn't find a category with this id ";
    public CategoryNotFoundException() {
        super(defaultMessage);
    }
}

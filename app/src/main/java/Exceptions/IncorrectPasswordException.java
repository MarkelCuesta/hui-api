package Exceptions;

public class IncorrectPasswordException extends Exception{
    private static final String defaultMessage = "Incorrect password.";

    public IncorrectPasswordException() {
        super(defaultMessage);
    }
    public IncorrectPasswordException(String message) {
        super(message);
    }
}

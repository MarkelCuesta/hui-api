package Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EmailTakenException extends Exception{
    private static final String defaultMessage = "This email has already been taken";

    public EmailTakenException(String message) {
        super(message);
    }

    public EmailTakenException() {
        super(defaultMessage);
    }
}

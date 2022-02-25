package Exceptions;

public class UsernameTakenException extends Exception {
        private static final String defaultMessage = "This username has already been taken";

        public UsernameTakenException(String message) { super(message); }
        public UsernameTakenException() {
            super(defaultMessage);
        }

}

package fr.elercia.redcloud.exceptions;

public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}

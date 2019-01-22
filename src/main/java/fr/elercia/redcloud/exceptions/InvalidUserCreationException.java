package fr.elercia.redcloud.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class InvalidUserCreationException extends Exception {

    public InvalidUserCreationException(String message) {
        super(message);
    }

    public InvalidUserCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUserCreationException(Throwable cause) {
        super(cause);
    }
}

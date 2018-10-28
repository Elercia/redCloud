package fr.elercia.redcloud.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class WrongLoginException extends Exception {
    public WrongLoginException() {
    }

    public WrongLoginException(String message) {
        super(message);
    }

    public WrongLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongLoginException(Throwable cause) {
        super(cause);
    }
}

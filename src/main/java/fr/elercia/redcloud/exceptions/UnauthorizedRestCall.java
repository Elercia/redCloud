package fr.elercia.redcloud.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedRestCall extends Exception {
    public UnauthorizedRestCall() {
    }

    public UnauthorizedRestCall(String message) {
        super(message);
    }

    public UnauthorizedRestCall(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedRestCall(Throwable cause) {
        super(cause);
    }
}

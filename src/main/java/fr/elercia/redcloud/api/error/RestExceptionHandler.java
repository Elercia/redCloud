package fr.elercia.redcloud.api.error;

import fr.elercia.redcloud.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(Throwable.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Throwable ex, WebRequest request) {

        ex.printStackTrace();

        LOG.debug("Handle rest exception", "exception", ex.getClass().getSimpleName(), "Message", ex.getMessage(), "Throws at", (ex.getStackTrace().length > 0 ? ex.getStackTrace()[0] : "none"));

        HttpStatus status;
        String message;

        if (ex instanceof ResourceNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            message = "Resource not found";
        } else if (ex instanceof DatabaseRuntimeException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "Internal server error";
        } else if (ex instanceof TokenNotFoundException) {
            status = HttpStatus.BAD_REQUEST;
            message = "Invalid token";
        } else if (ex instanceof InvalidTokenException) {
            status = HttpStatus.UNAUTHORIZED;
            message = "Invalid token";
        } else if (ex instanceof UnauthorizedRestCall || ex instanceof InvalidLoginException) {
            status = HttpStatus.FORBIDDEN;
            message = "Forbidden access";
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "[" + ex.getClass().getSimpleName() + "] " + (ex.getMessage() != null ? ex.getMessage() : "");
        }

        ErrorDetails errorDetails = new ErrorDetails(Instant.now(), message, request.getDescription(true));

        return new ResponseEntity<>(errorDetails, status);
    }
}

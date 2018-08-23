package fr.elercia.redcloud.api.error;

import fr.elercia.redcloud.logging.LoggerWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final LoggerWrapper LOG = new LoggerWrapper(RestExceptionHandler.class);

    @ExceptionHandler(Throwable.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Throwable ex, WebRequest request) {

        LOG.error("Handle rest exception", "exception", ex.getClass().getSimpleName());
        ex.printStackTrace();

        ErrorDetails errorDetails = new ErrorDetails(new Date(), "[" + ex.getClass().getSimpleName() + "] " + ex.getMessage(), request.getDescription(true));
//        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Internal server error.", request.getDescription(true));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
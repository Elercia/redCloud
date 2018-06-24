package fr.elercia.redcloud.exceptions;

public class DatabaseRuntimeException extends RuntimeException {
    public DatabaseRuntimeException() {
    }

    public DatabaseRuntimeException(String message) {
        super(message);
    }

    public DatabaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseRuntimeException(Throwable cause) {
        super(cause);
    }
}


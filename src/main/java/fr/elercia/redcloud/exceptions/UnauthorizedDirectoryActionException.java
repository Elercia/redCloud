package fr.elercia.redcloud.exceptions;

public class UnauthorizedDirectoryActionException extends Exception {
    public UnauthorizedDirectoryActionException() {
    }

    public UnauthorizedDirectoryActionException(String message) {
        super(message);
    }

    public UnauthorizedDirectoryActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedDirectoryActionException(Throwable cause) {
        super(cause);
    }
}

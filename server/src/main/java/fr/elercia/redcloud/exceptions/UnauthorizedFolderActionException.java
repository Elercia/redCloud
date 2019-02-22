package fr.elercia.redcloud.exceptions;

public class UnauthorizedFolderActionException extends Exception {
    public UnauthorizedFolderActionException() {
    }

    public UnauthorizedFolderActionException(String message) {
        super(message);
    }

    public UnauthorizedFolderActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedFolderActionException(Throwable cause) {
        super(cause);
    }
}

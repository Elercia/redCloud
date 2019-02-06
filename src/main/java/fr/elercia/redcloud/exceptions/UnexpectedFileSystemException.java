package fr.elercia.redcloud.exceptions;

public class UnexpectedFileSystemException extends RuntimeException {

    public UnexpectedFileSystemException() {
    }

    public UnexpectedFileSystemException(String message) {
        super(message);
    }

    public UnexpectedFileSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnexpectedFileSystemException(Throwable cause) {
        super(cause);
    }
}

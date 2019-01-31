package fr.elercia.redcloud.exceptions;

public class DirectoryNotFoundException extends ResourceNotFoundException {

    public DirectoryNotFoundException() {
    }

    public DirectoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DirectoryNotFoundException(String message) {
        super(message);
    }
}

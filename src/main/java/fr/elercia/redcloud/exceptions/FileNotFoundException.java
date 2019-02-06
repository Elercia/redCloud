package fr.elercia.redcloud.exceptions;

public class FileNotFoundException extends ResourceNotFoundException {

    public FileNotFoundException() {
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNotFoundException(String message) {
        super(message);
    }
}

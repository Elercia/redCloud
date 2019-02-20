package fr.elercia.redcloud.exceptions;

public class FileNameFormatException extends ResourceNotFoundException {

    public FileNameFormatException() {
    }

    public FileNameFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNameFormatException(String message) {
        super(message);
    }
}

package fr.elercia.redcloud.exceptions;

public class FileStorageException extends ResourceNotFoundException {

    public FileStorageException() {
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(Throwable cause) {
        super(cause);
    }
}

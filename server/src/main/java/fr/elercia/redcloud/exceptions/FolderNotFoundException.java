package fr.elercia.redcloud.exceptions;

public class FolderNotFoundException extends ResourceNotFoundException {

    public FolderNotFoundException() {
    }

    public FolderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FolderNotFoundException(String message) {
        super(message);
    }
}

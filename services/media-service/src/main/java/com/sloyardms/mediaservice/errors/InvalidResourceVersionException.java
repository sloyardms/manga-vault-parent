package com.sloyardms.mediaservice.errors;

public class InvalidResourceVersionException extends RuntimeException {

    public InvalidResourceVersionException(String message) {
        super(message);
    }

    public InvalidResourceVersionException(Throwable cause) {
        super(cause);
    }
    public InvalidResourceVersionException(String message, Throwable cause) {
        super(message, cause);
    }
}

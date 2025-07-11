package com.sloyardms.mediaservice.exception;

public class ResourceDuplicatedException  extends RuntimeException{

    public ResourceDuplicatedException(String message) {
        super(message);
    }

    public ResourceDuplicatedException(Throwable cause) {
        super(cause);
    }

    public ResourceDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }

}
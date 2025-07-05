package com.sloyardms.mediaparser.api.exceptions;

public class MediaParserException extends Exception{

    public MediaParserException(String message) {
        super(message);
    }

    public MediaParserException(String message, Throwable cause) {
        super(message, cause);
    }
}

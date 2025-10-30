package com.dailyhealthreminder.exception;

/**
 * Exception thrown when a request is invalid or malformed.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
public class BadRequestException extends RuntimeException {

    /**
     * Constructor with message.
     * 
     * @param message Exception message
     */
    public BadRequestException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause.
     * 
     * @param message Exception message
     * @param cause Exception cause
     */
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
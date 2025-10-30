package com.dailyhealthreminder.exception;

/**
 * Exception thrown when a user is not authorized to perform an action.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
public class UnauthorizedException extends RuntimeException {

    /**
     * Constructor with message.
     * 
     * @param message Exception message
     */
    public UnauthorizedException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause.
     * 
     * @param message Exception message
     * @param cause Exception cause
     */
    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
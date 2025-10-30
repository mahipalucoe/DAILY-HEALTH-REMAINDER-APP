package com.dailyhealthreminder.exception;

/**
 * Exception thrown when a requested resource is not found.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor with message.
     * 
     * @param message Exception message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause.
     * 
     * @param message Exception message
     * @param cause Exception cause
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for resource not found with resource details.
     * 
     * @param resourceName Name of the resource
     * @param fieldName Field name
     * @param fieldValue Field value
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
    }
}
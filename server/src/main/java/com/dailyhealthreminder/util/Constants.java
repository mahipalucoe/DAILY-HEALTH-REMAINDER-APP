package com.dailyhealthreminder.util;

/**
 * Application-wide constants.
 * Contains all constant values used throughout the application.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
public final class Constants {

    private Constants() {
        // Private constructor to prevent instantiation
    }

    // API Endpoints
    public static final String API_BASE_PATH = "/api/v1";
    public static final String AUTH_BASE_PATH = API_BASE_PATH + "/auth";
    public static final String USER_BASE_PATH = API_BASE_PATH + "/users";
    public static final String REMINDER_BASE_PATH = API_BASE_PATH + "/reminders";
    public static final String HEALTH_LOG_BASE_PATH = API_BASE_PATH + "/health-logs";

    // Security
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String[] PUBLIC_URLS = {
        "/api/v1/auth/**",
        "/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/v3/api-docs/**"
    };

    // Default Role
    public static final String DEFAULT_ROLE = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    // Response Messages
    public static final String SUCCESS = "Success";
    public static final String CREATED = "Created successfully";
    public static final String UPDATED = "Updated successfully";
    public static final String DELETED = "Deleted successfully";
    public static final String NOT_FOUND = "Resource not found";
    public static final String UNAUTHORIZED = "Unauthorized access";
    public static final String BAD_REQUEST = "Bad request";
    public static final String INTERNAL_SERVER_ERROR = "Internal server error";

    // Validation Messages
    public static final String EMAIL_REQUIRED = "Email is required";
    public static final String EMAIL_INVALID = "Email is invalid";
    public static final String PASSWORD_REQUIRED = "Password is required";
    public static final String PASSWORD_MIN_LENGTH = "Password must be at least 6 characters";
    public static final String NAME_REQUIRED = "Name is required";

    // Health Log Types
    public static final String HEALTH_LOG_TYPE_WEIGHT = "WEIGHT";
    public static final String HEALTH_LOG_TYPE_EXERCISE = "EXERCISE";
    public static final String HEALTH_LOG_TYPE_SLEEP = "SLEEP";
    public static final String HEALTH_LOG_TYPE_WATER = "WATER";
    public static final String HEALTH_LOG_TYPE_MOOD = "MOOD";
    public static final String HEALTH_LOG_TYPE_MEDICATION = "MEDICATION";

    // Reminder Frequencies
    public static final String FREQUENCY_DAILY = "DAILY";
    public static final String FREQUENCY_WEEKLY = "WEEKLY";
    public static final String FREQUENCY_MONTHLY = "MONTHLY";
}
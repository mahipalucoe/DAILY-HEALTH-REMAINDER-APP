package com.dailyhealthreminder.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for user response.
 * Contains user information for API responses.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    /**
     * User's unique identifier.
     */
    private String id;

    /**
     * User's full name.
     */
    private String name;

    /**
     * User's email address.
     */
    private String email;

    /**
     * User's phone number.
     */
    private String phoneNumber;

    /**
     * User's date of birth.
     */
    private LocalDateTime dateOfBirth;

    /**
     * User's gender.
     */
    private String gender;

    /**
     * User's profile picture URL.
     */
    private String profilePictureUrl;

    /**
     * User's roles.
     */
    private Set<String> roles;

    /**
     * Flag indicating if the user is enabled.
     */
    private boolean enabled;

    /**
     * Timestamp when the user was created.
     */
    private LocalDateTime createdAt;

    /**
     * Timestamp when the user was last updated.
     */
    private LocalDateTime updatedAt;
}
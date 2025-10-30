package com.dailyhealthreminder.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for authentication response.
 * Contains JWT tokens and user information after successful authentication.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {

    /**
     * JWT access token.
     */
    private String accessToken;

    /**
     * JWT refresh token.
     */
    private String refreshToken;

    /**
     * Token type (usually "Bearer").
     */
    @Builder.Default
    private String tokenType = "Bearer";

    /**
     * User information.
     */
    private UserResponse user;

    /**
     * Access token expiry time in milliseconds.
     */
    private Long expiresIn;
}
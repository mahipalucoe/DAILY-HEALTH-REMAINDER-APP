package com.dailyhealthreminder.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

/**
 * Entity class representing a refresh token.
 * Refresh tokens are used to obtain new access tokens without re-authentication.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "refresh_tokens")
public class RefreshToken {

    /**
     * Unique identifier for the refresh token.
     */
    @Id
    private String id;

    /**
     * The actual token string.
     * Must be unique.
     */
    @Indexed(unique = true)
    private String token;

    /**
     * Reference to the user who owns this token.
     */
    @DBRef
    private User user;

    /**
     * Expiry date and time of the token.
     */
    @Indexed(expireAfterSeconds = 0)
    private LocalDateTime expiryDate;

    /**
     * Timestamp when the token was created.
     */
    @CreatedDate
    private LocalDateTime createdAt;

    /**
     * Flag indicating if the token has been revoked.
     */
    @Builder.Default
    private boolean revoked = false;

    /**
     * Checks if the refresh token is expired.
     * 
     * @return true if token is expired
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }
}
package com.dailyhealthreminder.repository;

import com.dailyhealthreminder.entity.RefreshToken;
import com.dailyhealthreminder.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for RefreshToken entity.
 * Provides database operations for refresh tokens.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {

    /**
     * Find a refresh token by token string.
     * 
     * @param token Token string
     * @return Optional containing the refresh token if found
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * Find a refresh token by user.
     * 
     * @param user User entity
     * @return Optional containing the refresh token if found
     */
    Optional<RefreshToken> findByUser(User user);

    /**
     * Delete all refresh tokens for a specific user.
     * 
     * @param user User entity
     */
    void deleteByUser(User user);

    /**
     * Delete a refresh token by token string.
     * 
     * @param token Token string
     */
    void deleteByToken(String token);
}
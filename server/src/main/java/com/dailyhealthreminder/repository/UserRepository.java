package com.dailyhealthreminder.repository;

import com.dailyhealthreminder.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entity.
 * Provides database operations for users.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Find a user by email address.
     * 
     * @param email User's email
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if a user exists by email.
     * 
     * @param email User's email
     * @return true if user exists
     */
    boolean existsByEmail(String email);

    /**
     * Check if a user exists by phone number.
     * 
     * @param phoneNumber User's phone number
     * @return true if user exists
     */
    boolean existsByPhoneNumber(String phoneNumber);
}
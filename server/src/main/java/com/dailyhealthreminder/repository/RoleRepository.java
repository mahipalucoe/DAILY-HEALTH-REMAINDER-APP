package com.dailyhealthreminder.repository;

import com.dailyhealthreminder.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Role entity.
 * Provides database operations for roles.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

    /**
     * Find a role by its name.
     * 
     * @param name Role name
     * @return Optional containing the role if found
     */
    Optional<Role> findByName(String name);

    /**
     * Check if a role exists by name.
     * 
     * @param name Role name
     * @return true if role exists
     */
    boolean existsByName(String name);
}
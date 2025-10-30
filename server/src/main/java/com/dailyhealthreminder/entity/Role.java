package com.dailyhealthreminder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Entity class representing a user role in the system.
 * Roles are used for authorization and access control.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "roles")
public class Role {

    /**
     * Unique identifier for the role.
     */
    @Id
    private String id;

    /**
     * Name of the role (e.g., ROLE_USER, ROLE_ADMIN).
     * Must be unique.
     */
    @Indexed(unique = true)
    private String name;

    /**
     * Description of the role.
     */
    private String description;

    /**
     * Constructor with name parameter.
     * 
     * @param name Role name
     */
    public Role(String name) {
        this.name = name;
    }
}
package com.dailyhealthreminder.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Entity class representing a user in the system.
 * Implements UserDetails for Spring Security integration.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "users")
public class User implements UserDetails {

    /**
     * Unique identifier for the user.
     */
    @Id
    private String id;

    /**
     * User's full name.
     */
    private String name;

    /**
     * User's email address (used as username).
     * Must be unique.
     */
    @Indexed(unique = true)
    private String email;

    /**
     * Encrypted password.
     */
    private String password;

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
     * Flag indicating if the user account is active.
     */
    @Builder.Default
    private boolean enabled = true;

    /**
     * Flag indicating if the user account is locked.
     */
    @Builder.Default
    private boolean accountNonLocked = true;

    /**
     * Set of roles assigned to the user.
     */
    @DBRef
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    /**
     * Timestamp when the user was created.
     */
    @CreatedDate
    private LocalDateTime createdAt;

    /**
     * Timestamp when the user was last updated.
     */
    @LastModifiedDate
    private LocalDateTime updatedAt;

    /**
     * Returns the authorities granted to the user.
     * 
     * @return Collection of granted authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    /**
     * Returns the username used to authenticate the user.
     * 
     * @return User's email
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Indicates whether the user's account has expired.
     * 
     * @return true if account is non-expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     * 
     * @return true if account is non-locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    /**
     * Indicates whether the user's credentials have expired.
     * 
     * @return true if credentials are non-expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     * 
     * @return true if user is enabled
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
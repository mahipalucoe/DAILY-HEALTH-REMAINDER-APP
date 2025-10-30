package com.dailyhealthreminder.service;

import com.dailyhealthreminder.dto.response.UserResponse;
import com.dailyhealthreminder.entity.Role;
import com.dailyhealthreminder.entity.User;
import com.dailyhealthreminder.exception.ResourceNotFoundException;
import com.dailyhealthreminder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

/**
 * Service for user operations.
 * Handles user-related business logic.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Load user by username (email) for Spring Security.
     * 
     * @param username Username (email)
     * @return UserDetails
     * @throws UsernameNotFoundException if user not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }

    /**
     * Get user profile by email.
     * 
     * @param email User email
     * @return UserResponse DTO
     */
    @Transactional(readOnly = true)
    public UserResponse getUserProfile(String email) {
        log.info("Getting user profile for email: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        return mapToUserResponse(user);
    }

    /**
     * Get user by ID.
     * 
     * @param userId User ID
     * @return UserResponse DTO
     */
    @Transactional(readOnly = true)
    public UserResponse getUserById(String userId) {
        log.info("Getting user by ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return mapToUserResponse(user);
    }

    /**
     * Update user profile.
     * 
     * @param email User email
     * @param name New name
     * @param phoneNumber New phone number
     * @param gender New gender
     * @return Updated UserResponse DTO
     */
    @Transactional
    public UserResponse updateUserProfile(String email, String name, String phoneNumber, String gender) {
        log.info("Updating user profile for email: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        if (name != null && !name.isBlank()) {
            user.setName(name);
        }
        if (phoneNumber != null) {
            user.setPhoneNumber(phoneNumber);
        }
        if (gender != null) {
            user.setGender(gender);
        }

        User updatedUser = userRepository.save(user);
        log.info("User profile updated successfully: {}", email);

        return mapToUserResponse(updatedUser);
    }

    /**
     * Update user profile picture.
     * 
     * @param email User email
     * @param profilePictureUrl Profile picture URL
     * @return Updated UserResponse DTO
     */
    @Transactional
    public UserResponse updateProfilePicture(String email, String profilePictureUrl) {
        log.info("Updating profile picture for email: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        user.setProfilePictureUrl(profilePictureUrl);
        User updatedUser = userRepository.save(user);

        log.info("Profile picture updated successfully: {}", email);
        return mapToUserResponse(updatedUser);
    }

    /**
     * Delete user account.
     * 
     * @param email User email
     */
    @Transactional
    public void deleteUser(String email) {
        log.info("Deleting user account: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        userRepository.delete(user);
        log.info("User account deleted successfully: {}", email);
    }

    /**
     * Map User entity to UserResponse DTO.
     * 
     * @param user User entity
     * @return UserResponse DTO
     */
    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .dateOfBirth(user.getDateOfBirth())
                .gender(user.getGender())
                .profilePictureUrl(user.getProfilePictureUrl())
                .roles(user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()))
                .enabled(user.isEnabled())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
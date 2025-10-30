package com.dailyhealthreminder.controller;

import com.dailyhealthreminder.dto.response.ApiResponse;
import com.dailyhealthreminder.dto.response.UserResponse;
import com.dailyhealthreminder.service.UserService;
import com.dailyhealthreminder.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for user management endpoints.
 * Handles user profile operations.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@RestController
@RequestMapping(Constants.USER_BASE_PATH)
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "User", description = "User management APIs")
public class UserController {

    private final UserService userService;

    /**
     * Get current user's profile.
     * 
     * @param authentication Authentication object
     * @return User profile response
     */
    @GetMapping("/profile")
    @Operation(summary = "Get user profile", description = "Get current authenticated user's profile")
    public ResponseEntity<ApiResponse<UserResponse>> getUserProfile(Authentication authentication) {
        String email = authentication.getName();
        UserResponse userResponse = userService.getUserProfile(email);
        ApiResponse<UserResponse> response = ApiResponse.success(Constants.SUCCESS, userResponse);
        return ResponseEntity.ok(response);
    }

    /**
     * Get user by ID.
     * 
     * @param userId User ID
     * @return User response
     */
    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID", description = "Get user information by user ID")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable String userId) {
        UserResponse userResponse = userService.getUserById(userId);
        ApiResponse<UserResponse> response = ApiResponse.success(Constants.SUCCESS, userResponse);
        return ResponseEntity.ok(response);
    }

    /**
     * Update user profile.
     * 
     * @param authentication Authentication object
     * @param name New name
     * @param phoneNumber New phone number
     * @param gender New gender
     * @return Updated user profile
     */
    @PutMapping("/profile")
    @Operation(summary = "Update user profile", description = "Update current user's profile information")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserProfile(
            Authentication authentication,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String gender) {
        String email = authentication.getName();
        UserResponse userResponse = userService.updateUserProfile(email, name, phoneNumber, gender);
        ApiResponse<UserResponse> response = ApiResponse.success(Constants.UPDATED, userResponse);
        return ResponseEntity.ok(response);
    }

    /**
     * Update profile picture.
     * 
     * @param authentication Authentication object
     * @param profilePictureUrl Profile picture URL
     * @return Updated user profile
     */
    @PatchMapping("/profile/picture")
    @Operation(summary = "Update profile picture", description = "Update user's profile picture URL")
    public ResponseEntity<ApiResponse<UserResponse>> updateProfilePicture(
            Authentication authentication,
            @RequestParam String profilePictureUrl) {
        String email = authentication.getName();
        UserResponse userResponse = userService.updateProfilePicture(email, profilePictureUrl);
        ApiResponse<UserResponse> response = ApiResponse.success(Constants.UPDATED, userResponse);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete user account.
     * 
     * @param authentication Authentication object
     * @return Success response
     */
    @DeleteMapping("/profile")
    @Operation(summary = "Delete user account", description = "Delete current user's account")
    public ResponseEntity<ApiResponse<String>> deleteUser(Authentication authentication) {
        String email = authentication.getName();
        userService.deleteUser(email);
        ApiResponse<String> response = ApiResponse.success(Constants.DELETED);
        return ResponseEntity.ok(response);
    }
}
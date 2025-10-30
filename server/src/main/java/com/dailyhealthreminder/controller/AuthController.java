package com.dailyhealthreminder.controller;

import com.dailyhealthreminder.dto.request.LoginRequest;
import com.dailyhealthreminder.dto.request.RegisterRequest;
import com.dailyhealthreminder.dto.response.ApiResponse;
import com.dailyhealthreminder.dto.response.AuthResponse;
import com.dailyhealthreminder.service.AuthService;
import com.dailyhealthreminder.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for authentication endpoints.
 * Handles user registration, login, token refresh, and logout.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@RestController
@RequestMapping(Constants.AUTH_BASE_PATH)
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {

    private final AuthService authService;

    /**
     * Register a new user.
     * 
     * @param request Registration request
     * @return Authentication response with tokens
     */
    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Create a new user account")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse authResponse = authService.register(request);
        ApiResponse<AuthResponse> response = ApiResponse.success(Constants.CREATED, authResponse);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Login user.
     * 
     * @param request Login request
     * @return Authentication response with tokens
     */
    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Authenticate user and return JWT tokens")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);
        ApiResponse<AuthResponse> response = ApiResponse.success(Constants.SUCCESS, authResponse);
        return ResponseEntity.ok(response);
    }

    /**
     * Refresh access token.
     * 
     * @param refreshToken Refresh token
     * @return Authentication response with new access token
     */
    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token", description = "Get a new access token using refresh token")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(
            @RequestParam("refreshToken") String refreshToken) {
        AuthResponse authResponse = authService.refreshToken(refreshToken);
        ApiResponse<AuthResponse> response = ApiResponse.success(Constants.SUCCESS, authResponse);
        return ResponseEntity.ok(response);
    }

    /**
     * Logout user.
     * 
     * @param authentication Authentication object
     * @return Success response
     */
    @PostMapping("/logout")
    @Operation(summary = "Logout user", description = "Revoke user's refresh token")
    public ResponseEntity<ApiResponse<String>> logout(Authentication authentication) {
        String email = authentication.getName();
        authService.logout(email);
        ApiResponse<String> response = ApiResponse.success("Logged out successfully");
        return ResponseEntity.ok(response);
    }
}
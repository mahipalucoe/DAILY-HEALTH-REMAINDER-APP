package com.dailyhealthreminder.service;

import com.dailyhealthreminder.dto.request.LoginRequest;
import com.dailyhealthreminder.dto.request.RegisterRequest;
import com.dailyhealthreminder.dto.response.AuthResponse;
import com.dailyhealthreminder.dto.response.UserResponse;
import com.dailyhealthreminder.entity.RefreshToken;
import com.dailyhealthreminder.entity.Role;
import com.dailyhealthreminder.entity.User;
import com.dailyhealthreminder.exception.BadRequestException;
import com.dailyhealthreminder.exception.ResourceNotFoundException;
import com.dailyhealthreminder.exception.UnauthorizedException;
import com.dailyhealthreminder.repository.RefreshTokenRepository;
import com.dailyhealthreminder.repository.RoleRepository;
import com.dailyhealthreminder.repository.UserRepository;
import com.dailyhealthreminder.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for authentication operations.
 * Handles user registration, login, and token management.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Value("${jwt.refresh-expiration}")
    private Long refreshTokenExpiration;

    /**
     * Register a new user.
     * 
     * @param request Registration request
     * @return Authentication response with tokens
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        log.info("Registering new user with email: {}", request.getEmail());

        // Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered");
        }

        // Get or create default role
        Role userRole = roleRepository.findByName(Constants.DEFAULT_ROLE)
                .orElseGet(() -> {
                    Role newRole = new Role(Constants.DEFAULT_ROLE);
                    return roleRepository.save(newRole);
                });

        // Create new user
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .enabled(true)
                .accountNonLocked(true)
                .roles(roles)
                .build();

        User savedUser = userRepository.save(user);
        log.info("User registered successfully: {}", savedUser.getEmail());

        // Generate tokens
        String accessToken = jwtService.generateToken(savedUser);
        RefreshToken refreshToken = createRefreshToken(savedUser);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .expiresIn(jwtService.getExpirationTime())
                .user(mapToUserResponse(savedUser))
                .build();
    }

    /**
     * Authenticate user and generate tokens.
     * 
     * @param request Login request
     * @return Authentication response with tokens
     */
    @Transactional
    public AuthResponse login(LoginRequest request) {
        log.info("User login attempt: {}", request.getEmail());

        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Get user
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Generate tokens
        String accessToken = jwtService.generateToken(user);
        
        // Delete old refresh token if exists
        refreshTokenRepository.findByUser(user)
                .ifPresent(refreshTokenRepository::delete);
        
        RefreshToken refreshToken = createRefreshToken(user);

        log.info("User logged in successfully: {}", user.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .expiresIn(jwtService.getExpirationTime())
                .user(mapToUserResponse(user))
                .build();
    }

    /**
     * Refresh access token using refresh token.
     * 
     * @param refreshTokenStr Refresh token
     * @return Authentication response with new tokens
     */
    @Transactional
    public AuthResponse refreshToken(String refreshTokenStr) {
        log.info("Refreshing access token");

        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenStr)
                .orElseThrow(() -> new UnauthorizedException("Invalid refresh token"));

        if (refreshToken.isRevoked()) {
            throw new UnauthorizedException("Refresh token has been revoked");
        }

        if (refreshToken.isExpired()) {
            refreshTokenRepository.delete(refreshToken);
            throw new UnauthorizedException("Refresh token has expired");
        }

        User user = refreshToken.getUser();
        String newAccessToken = jwtService.generateToken(user);

        log.info("Access token refreshed for user: {}", user.getEmail());

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshTokenStr)
                .tokenType("Bearer")
                .expiresIn(jwtService.getExpirationTime())
                .user(mapToUserResponse(user))
                .build();
    }

    /**
     * Logout user by revoking refresh token.
     * 
     * @param email User email
     */
    @Transactional
    public void logout(String email) {
        log.info("Logging out user: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        refreshTokenRepository.findByUser(user)
                .ifPresent(refreshToken -> {
                    refreshToken.setRevoked(true);
                    refreshTokenRepository.save(refreshToken);
                });

        log.info("User logged out successfully: {}", email);
    }

    /**
     * Create a new refresh token for user.
     * 
     * @param user User entity
     * @return Created refresh token
     */
    private RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(LocalDateTime.now().plusSeconds(refreshTokenExpiration / 1000))
                .revoked(false)
                .build();

        return refreshTokenRepository.save(refreshToken);
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
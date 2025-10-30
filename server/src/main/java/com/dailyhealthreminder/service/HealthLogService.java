package com.dailyhealthreminder.service;

import com.dailyhealthreminder.dto.request.HealthLogRequest;
import com.dailyhealthreminder.dto.response.HealthLogResponse;
import com.dailyhealthreminder.entity.HealthLog;
import com.dailyhealthreminder.entity.User;
import com.dailyhealthreminder.exception.ResourceNotFoundException;
import com.dailyhealthreminder.exception.UnauthorizedException;
import com.dailyhealthreminder.repository.HealthLogRepository;
import com.dailyhealthreminder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for health log operations.
 * Handles health log-related business logic.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class HealthLogService {

    private final HealthLogRepository healthLogRepository;
    private final UserRepository userRepository;

    /**
     * Create a new health log entry.
     * 
     * @param email User email
     * @param request Health log request
     * @return Created health log response
     */
    @Transactional
    public HealthLogResponse createHealthLog(String email, HealthLogRequest request) {
        log.info("Creating health log for user: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        HealthLog healthLog = HealthLog.builder()
                .user(user)
                .logType(request.getLogType())
                .logDate(request.getLogDate())
                .value(request.getValue())
                .unit(request.getUnit())
                .metadata(request.getMetadata())
                .notes(request.getNotes())
                .durationMinutes(request.getDurationMinutes())
                .caloriesBurned(request.getCaloriesBurned())
                .qualityRating(request.getQualityRating())
                .build();

        HealthLog savedLog = healthLogRepository.save(healthLog);
        log.info("Health log created successfully with ID: {}", savedLog.getId());

        return mapToHealthLogResponse(savedLog);
    }

    /**
     * Get all health logs for a user with pagination.
     * 
     * @param email User email
     * @param page Page number
     * @param size Page size
     * @return Page of health log responses
     */
    @Transactional(readOnly = true)
    public Page<HealthLogResponse> getUserHealthLogs(String email, int page, int size) {
        log.info("Getting health logs for user: {} (page: {}, size: {})", email, page, size);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "logDate"));
        Page<HealthLog> healthLogs = healthLogRepository.findByUserOrderByLogDateDesc(user, pageable);

        return healthLogs.map(this::mapToHealthLogResponse);
    }

    /**
     * Get health logs by type.
     * 
     * @param email User email
     * @param logType Log type
     * @param page Page number
     * @param size Page size
     * @return Page of health log responses
     */
    @Transactional(readOnly = true)
    public Page<HealthLogResponse> getHealthLogsByType(String email, String logType, int page, int size) {
        log.info("Getting health logs by type {} for user: {}", logType, email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "logDate"));
        Page<HealthLog> healthLogs = healthLogRepository.findByUserAndLogType(user, logType, pageable);

        return healthLogs.map(this::mapToHealthLogResponse);
    }

    /**
     * Get health logs within a date range.
     * 
     * @param email User email
     * @param startDate Start date
     * @param endDate End date
     * @param page Page number
     * @param size Page size
     * @return Page of health log responses
     */
    @Transactional(readOnly = true)
    public Page<HealthLogResponse> getHealthLogsByDateRange(
            String email, LocalDateTime startDate, LocalDateTime endDate, int page, int size) {
        log.info("Getting health logs for user {} from {} to {}", email, startDate, endDate);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "logDate"));
        Page<HealthLog> healthLogs = healthLogRepository.findByUserAndLogDateBetween(
                user, startDate, endDate, pageable);

        return healthLogs.map(this::mapToHealthLogResponse);
    }

    /**
     * Get health logs by type and date range.
     * 
     * @param email User email
     * @param logType Log type
     * @param startDate Start date
     * @param endDate End date
     * @return List of health log responses
     */
    @Transactional(readOnly = true)
    public List<HealthLogResponse> getHealthLogsByTypeAndDateRange(
            String email, String logType, LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Getting health logs by type {} for user {} from {} to {}", 
                logType, email, startDate, endDate);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        List<HealthLog> healthLogs = healthLogRepository.findByUserAndLogTypeAndLogDateBetween(
                user, logType, startDate, endDate);

        return healthLogs.stream()
                .map(this::mapToHealthLogResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get a specific health log by ID.
     * 
     * @param email User email
     * @param logId Health log ID
     * @return Health log response
     */
    @Transactional(readOnly = true)
    public HealthLogResponse getHealthLogById(String email, String logId) {
        log.info("Getting health log with ID: {} for user: {}", logId, email);

        HealthLog healthLog = healthLogRepository.findById(logId)
                .orElseThrow(() -> new ResourceNotFoundException("HealthLog", "id", logId));

        // Check if log belongs to user
        if (!healthLog.getUser().getEmail().equals(email)) {
            throw new UnauthorizedException("You are not authorized to access this health log");
        }

        return mapToHealthLogResponse(healthLog);
    }

    /**
     * Update a health log.
     * 
     * @param email User email
     * @param logId Health log ID
     * @param request Health log request
     * @return Updated health log response
     */
    @Transactional
    public HealthLogResponse updateHealthLog(String email, String logId, HealthLogRequest request) {
        log.info("Updating health log with ID: {} for user: {}", logId, email);

        HealthLog healthLog = healthLogRepository.findById(logId)
                .orElseThrow(() -> new ResourceNotFoundException("HealthLog", "id", logId));

        // Check if log belongs to user
        if (!healthLog.getUser().getEmail().equals(email)) {
            throw new UnauthorizedException("You are not authorized to update this health log");
        }

        // Update fields
        if (request.getLogType() != null) {
            healthLog.setLogType(request.getLogType());
        }
        if (request.getLogDate() != null) {
            healthLog.setLogDate(request.getLogDate());
        }
        if (request.getValue() != null) {
            healthLog.setValue(request.getValue());
        }
        if (request.getUnit() != null) {
            healthLog.setUnit(request.getUnit());
        }
        if (request.getMetadata() != null) {
            healthLog.setMetadata(request.getMetadata());
        }
        if (request.getNotes() != null) {
            healthLog.setNotes(request.getNotes());
        }
        if (request.getDurationMinutes() != null) {
            healthLog.setDurationMinutes(request.getDurationMinutes());
        }
        if (request.getCaloriesBurned() != null) {
            healthLog.setCaloriesBurned(request.getCaloriesBurned());
        }
        if (request.getQualityRating() != null) {
            healthLog.setQualityRating(request.getQualityRating());
        }

        HealthLog updatedLog = healthLogRepository.save(healthLog);
        log.info("Health log updated successfully with ID: {}", updatedLog.getId());

        return mapToHealthLogResponse(updatedLog);
    }

    /**
     * Delete a health log.
     * 
     * @param email User email
     * @param logId Health log ID
     */
    @Transactional
    public void deleteHealthLog(String email, String logId) {
        log.info("Deleting health log with ID: {} for user: {}", logId, email);

        HealthLog healthLog = healthLogRepository.findById(logId)
                .orElseThrow(() -> new ResourceNotFoundException("HealthLog", "id", logId));

        // Check if log belongs to user
        if (!healthLog.getUser().getEmail().equals(email)) {
            throw new UnauthorizedException("You are not authorized to delete this health log");
        }

        healthLogRepository.delete(healthLog);
        log.info("Health log deleted successfully with ID: {}", logId);
    }

    /**
     * Get health log count for a user.
     * 
     * @param email User email
     * @return Health log count
     */
    @Transactional(readOnly = true)
    public long getHealthLogCount(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        return healthLogRepository.countByUser(user);
    }

    /**
     * Get health log count by type.
     * 
     * @param email User email
     * @param logType Log type
     * @return Health log count
     */
    @Transactional(readOnly = true)
    public long getHealthLogCountByType(String email, String logType) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        return healthLogRepository.countByUserAndLogType(user, logType);
    }

    /**
     * Map HealthLog entity to HealthLogResponse DTO.
     * 
     * @param healthLog HealthLog entity
     * @return HealthLogResponse DTO
     */
    private HealthLogResponse mapToHealthLogResponse(HealthLog healthLog) {
        return HealthLogResponse.builder()
                .id(healthLog.getId())
                .userId(healthLog.getUser().getId())
                .logType(healthLog.getLogType())
                .logDate(healthLog.getLogDate())
                .value(healthLog.getValue())
                .unit(healthLog.getUnit())
                .metadata(healthLog.getMetadata())
                .notes(healthLog.getNotes())
                .durationMinutes(healthLog.getDurationMinutes())
                .caloriesBurned(healthLog.getCaloriesBurned())
                .qualityRating(healthLog.getQualityRating())
                .createdAt(healthLog.getCreatedAt())
                .updatedAt(healthLog.getUpdatedAt())
                .build();
    }
}
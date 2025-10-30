package com.dailyhealthreminder.controller;

import com.dailyhealthreminder.dto.request.HealthLogRequest;
import com.dailyhealthreminder.dto.response.ApiResponse;
import com.dailyhealthreminder.dto.response.HealthLogResponse;
import com.dailyhealthreminder.service.HealthLogService;
import com.dailyhealthreminder.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST controller for health log management endpoints.
 * Handles CRUD operations for health logs.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@RestController
@RequestMapping(Constants.HEALTH_LOG_BASE_PATH)
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Health Log", description = "Health log management APIs")
public class HealthLogController {

    private final HealthLogService healthLogService;

    /**
     * Create a new health log.
     * 
     * @param authentication Authentication object
     * @param request Health log request
     * @return Created health log response
     */
    @PostMapping
    @Operation(summary = "Create health log", description = "Create a new health log entry")
    public ResponseEntity<ApiResponse<HealthLogResponse>> createHealthLog(
            Authentication authentication,
            @Valid @RequestBody HealthLogRequest request) {
        String email = authentication.getName();
        HealthLogResponse healthLogResponse = healthLogService.createHealthLog(email, request);
        ApiResponse<HealthLogResponse> response = ApiResponse.success(Constants.CREATED, healthLogResponse);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get all health logs for current user with pagination.
     * 
     * @param authentication Authentication object
     * @param page Page number (default: 0)
     * @param size Page size (default: 10)
     * @return Page of health logs
     */
    @GetMapping
    @Operation(summary = "Get all health logs", description = "Get all health logs for the current user with pagination")
    public ResponseEntity<ApiResponse<Page<HealthLogResponse>>> getUserHealthLogs(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        String email = authentication.getName();
        Page<HealthLogResponse> healthLogs = healthLogService.getUserHealthLogs(email, page, size);
        ApiResponse<Page<HealthLogResponse>> response = ApiResponse.success(Constants.SUCCESS, healthLogs);
        return ResponseEntity.ok(response);
    }

    /**
     * Get health logs by type.
     * 
     * @param authentication Authentication object
     * @param logType Log type
     * @param page Page number (default: 0)
     * @param size Page size (default: 10)
     * @return Page of health logs
     */
    @GetMapping("/type/{logType}")
    @Operation(summary = "Get health logs by type", description = "Get health logs filtered by type")
    public ResponseEntity<ApiResponse<Page<HealthLogResponse>>> getHealthLogsByType(
            Authentication authentication,
            @PathVariable String logType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        String email = authentication.getName();
        Page<HealthLogResponse> healthLogs = healthLogService.getHealthLogsByType(email, logType, page, size);
        ApiResponse<Page<HealthLogResponse>> response = ApiResponse.success(Constants.SUCCESS, healthLogs);
        return ResponseEntity.ok(response);
    }

    /**
     * Get health logs by date range.
     * 
     * @param authentication Authentication object
     * @param startDate Start date
     * @param endDate End date
     * @param page Page number (default: 0)
     * @param size Page size (default: 10)
     * @return Page of health logs
     */
    @GetMapping("/date-range")
    @Operation(summary = "Get health logs by date range", description = "Get health logs within a date range")
    public ResponseEntity<ApiResponse<Page<HealthLogResponse>>> getHealthLogsByDateRange(
            Authentication authentication,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        String email = authentication.getName();
        Page<HealthLogResponse> healthLogs = healthLogService.getHealthLogsByDateRange(
                email, startDate, endDate, page, size);
        ApiResponse<Page<HealthLogResponse>> response = ApiResponse.success(Constants.SUCCESS, healthLogs);
        return ResponseEntity.ok(response);
    }

    /**
     * Get health logs by type and date range.
     * 
     * @param authentication Authentication object
     * @param logType Log type
     * @param startDate Start date
     * @param endDate End date
     * @return List of health logs
     */
    @GetMapping("/type/{logType}/date-range")
    @Operation(summary = "Get health logs by type and date range", 
               description = "Get health logs filtered by type and date range")
    public ResponseEntity<ApiResponse<List<HealthLogResponse>>> getHealthLogsByTypeAndDateRange(
            Authentication authentication,
            @PathVariable String logType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        String email = authentication.getName();
        List<HealthLogResponse> healthLogs = healthLogService.getHealthLogsByTypeAndDateRange(
                email, logType, startDate, endDate);
        ApiResponse<List<HealthLogResponse>> response = ApiResponse.success(Constants.SUCCESS, healthLogs);
        return ResponseEntity.ok(response);
    }

    /**
     * Get health log by ID.
     * 
     * @param authentication Authentication object
     * @param logId Health log ID
     * @return Health log response
     */
    @GetMapping("/{logId}")
    @Operation(summary = "Get health log by ID", description = "Get a specific health log by ID")
    public ResponseEntity<ApiResponse<HealthLogResponse>> getHealthLogById(
            Authentication authentication,
            @PathVariable String logId) {
        String email = authentication.getName();
        HealthLogResponse healthLogResponse = healthLogService.getHealthLogById(email, logId);
        ApiResponse<HealthLogResponse> response = ApiResponse.success(Constants.SUCCESS, healthLogResponse);
        return ResponseEntity.ok(response);
    }

    /**
     * Update health log.
     * 
     * @param authentication Authentication object
     * @param logId Health log ID
     * @param request Health log request
     * @return Updated health log response
     */
    @PutMapping("/{logId}")
    @Operation(summary = "Update health log", description = "Update an existing health log")
    public ResponseEntity<ApiResponse<HealthLogResponse>> updateHealthLog(
            Authentication authentication,
            @PathVariable String logId,
            @Valid @RequestBody HealthLogRequest request) {
        String email = authentication.getName();
        HealthLogResponse healthLogResponse = healthLogService.updateHealthLog(email, logId, request);
        ApiResponse<HealthLogResponse> response = ApiResponse.success(Constants.UPDATED, healthLogResponse);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete health log.
     * 
     * @param authentication Authentication object
     * @param logId Health log ID
     * @return Success response
     */
    @DeleteMapping("/{logId}")
    @Operation(summary = "Delete health log", description = "Delete a health log")
    public ResponseEntity<ApiResponse<String>> deleteHealthLog(
            Authentication authentication,
            @PathPath logId) {
        String email = authentication.getName();
        healthLogService.deleteHealthLog(email, logId);
        ApiResponse<String> response = ApiResponse.success(Constants.DELETED);
        return ResponseEntity.ok(response);
    }

    /**
     * Get health log count.
     * 
     * @param authentication Authentication object
     * @return Health log count
     */
    @GetMapping("/count")
    @Operation(summary = "Get health log count", description = "Get total count of user's health logs")
    public ResponseEntity<ApiResponse<Long>> getHealthLogCount(Authentication authentication) {
        String email = authentication.getName();
        long count = healthLogService.getHealthLogCount(email);
        ApiResponse<Long> response = ApiResponse.success(Constants.SUCCESS, count);
        return ResponseEntity.ok(response);
    }

    /**
     * Get health log count by type.
     * 
     * @param authentication Authentication object
     * @param logType Log type
     * @return Health log count
     */
    @GetMapping("/count/type/{logType}")
    @Operation(summary = "Get health log count by type", 
               description = "Get count of user's health logs by type")
    public ResponseEntity<ApiResponse<Long>> getHealthLogCountByType(
            Authentication authentication,
            @PathVariable String logType) {
        String email = authentication.getName();
        long count = healthLogService.getHealthLogCountByType(email, logType);
        ApiResponse<Long> response = ApiResponse.success(Constants.SUCCESS, count);
        return ResponseEntity.ok(response);
    }
}
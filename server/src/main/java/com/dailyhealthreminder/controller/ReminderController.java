package com.dailyhealthreminder.controller;

import com.dailyhealthreminder.dto.request.ReminderRequest;
import com.dailyhealthreminder.dto.response.ApiResponse;
import com.dailyhealthreminder.dto.response.ReminderResponse;
import com.dailyhealthreminder.service.ReminderService;
import com.dailyhealthreminder.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for reminder management endpoints.
 * Handles CRUD operations for reminders.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@RestController
@RequestMapping(Constants.REMINDER_BASE_PATH)
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Reminder", description = "Reminder management APIs")
public class ReminderController {

    private final ReminderService reminderService;

    /**
     * Create a new reminder.
     * 
     * @param authentication Authentication object
     * @param request Reminder request
     * @return Created reminder response
     */
    @PostMapping
    @Operation(summary = "Create reminder", description = "Create a new reminder for the user")
    public ResponseEntity<ApiResponse<ReminderResponse>> createReminder(
            Authentication authentication,
            @Valid @RequestBody ReminderRequest request) {
        String email = authentication.getName();
        ReminderResponse reminderResponse = reminderService.createReminder(email, request);
        ApiResponse<ReminderResponse> response = ApiResponse.success(Constants.CREATED, reminderResponse);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get all reminders for current user.
     * 
     * @param authentication Authentication object
     * @return List of reminders
     */
    @GetMapping
    @Operation(summary = "Get all reminders", description = "Get all reminders for the current user")
    public ResponseEntity<ApiResponse<List<ReminderResponse>>> getUserReminders(
            Authentication authentication) {
        String email = authentication.getName();
        List<ReminderResponse> reminders = reminderService.getUserReminders(email);
        ApiResponse<List<ReminderResponse>> response = ApiResponse.success(Constants.SUCCESS, reminders);
        return ResponseEntity.ok(response);
    }

    /**
     * Get active reminders.
     * 
     * @param authentication Authentication object
     * @return List of active reminders
     */
    @GetMapping("/active")
    @Operation(summary = "Get active reminders", description = "Get all active reminders for the current user")
    public ResponseEntity<ApiResponse<List<ReminderResponse>>> getActiveReminders(
            Authentication authentication) {
        String email = authentication.getName();
        List<ReminderResponse> reminders = reminderService.getActiveReminders(email);
        ApiResponse<List<ReminderResponse>> response = ApiResponse.success(Constants.SUCCESS, reminders);
        return ResponseEntity.ok(response);
    }

    /**
     * Get reminders by type.
     * 
     * @param authentication Authentication object
     * @param reminderType Reminder type
     * @return List of reminders
     */
    @GetMapping("/type/{reminderType}")
    @Operation(summary = "Get reminders by type", description = "Get reminders filtered by type")
    public ResponseEntity<ApiResponse<List<ReminderResponse>>> getRemindersByType(
            Authentication authentication,
            @PathVariable String reminderType) {
        String email = authentication.getName();
        List<ReminderResponse> reminders = reminderService.getRemindersByType(email, reminderType);
        ApiResponse<List<ReminderResponse>> response = ApiResponse.success(Constants.SUCCESS, reminders);
        return ResponseEntity.ok(response);
    }

    /**
     * Get reminder by ID.
     * 
     * @param authentication Authentication object
     * @param reminderId Reminder ID
     * @return Reminder response
     */
    @GetMapping("/{reminderId}")
    @Operation(summary = "Get reminder by ID", description = "Get a specific reminder by ID")
    public ResponseEntity<ApiResponse<ReminderResponse>> getReminderById(
            Authentication authentication,
            @PathVariable String reminderId) {
        String email = authentication.getName();
        ReminderResponse reminderResponse = reminderService.getReminderById(email, reminderId);
        ApiResponse<ReminderResponse> response = ApiResponse.success(Constants.SUCCESS, reminderResponse);
        return ResponseEntity.ok(response);
    }

    /**
     * Update reminder.
     * 
     * @param authentication Authentication object
     * @param reminderId Reminder ID
     * @param request Reminder request
     * @return Updated reminder response
     */
    @PutMapping("/{reminderId}")
    @Operation(summary = "Update reminder", description = "Update an existing reminder")
    public ResponseEntity<ApiResponse<ReminderResponse>> updateReminder(
            Authentication authentication,
            @PathVariable String reminderId,
            @Valid @RequestBody ReminderRequest request) {
        String email = authentication.getName();
        ReminderResponse reminderResponse = reminderService.updateReminder(email, reminderId, request);
        ApiResponse<ReminderResponse> response = ApiResponse.success(Constants.UPDATED, reminderResponse);
        return ResponseEntity.ok(response);
    }

    /**
     * Toggle reminder status.
     * 
     * @param authentication Authentication object
     * @param reminderId Reminder ID
     * @return Updated reminder response
     */
    @PatchMapping("/{reminderId}/toggle")
    @Operation(summary = "Toggle reminder status", description = "Toggle reminder active/inactive status")
    public ResponseEntity<ApiResponse<ReminderResponse>> toggleReminderStatus(
            Authentication authentication,
            @PathVariable String reminderId) {
        String email = authentication.getName();
        ReminderResponse reminderResponse = reminderService.toggleReminderStatus(email, reminderId);
        ApiResponse<ReminderResponse> response = ApiResponse.success(Constants.UPDATED, reminderResponse);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete reminder.
     * 
     * @param authentication Authentication object
     * @param reminderId Reminder ID
     * @return Success response
     */
    @DeleteMapping("/{reminderId}")
    @Operation(summary = "Delete reminder", description = "Delete a reminder")
    public ResponseEntity<ApiResponse<String>> deleteReminder(
            Authentication authentication,
            @PathVariable String reminderId) {
        String email = authentication.getName();
        reminderService.deleteReminder(email, reminderId);
        ApiResponse<String> response = ApiResponse.success(Constants.DELETED);
        return ResponseEntity.ok(response);
    }

    /**
     * Get reminder count.
     * 
     * @param authentication Authentication object
     * @return Reminder count
     */
    @GetMapping("/count")
    @Operation(summary = "Get reminder count", description = "Get total count of user's reminders")
    public ResponseEntity<ApiResponse<Long>> getReminderCount(Authentication authentication) {
        String email = authentication.getName();
        long count = reminderService.getReminderCount(email);
        ApiResponse<Long> response = ApiResponse.success(Constants.SUCCESS, count);
        return ResponseEntity.ok(response);
    }
}
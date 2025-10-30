package com.dailyhealthreminder.service;

import com.dailyhealthreminder.dto.request.ReminderRequest;
import com.dailyhealthreminder.dto.response.ReminderResponse;
import com.dailyhealthreminder.entity.Reminder;
import com.dailyhealthreminder.entity.User;
import com.dailyhealthreminder.exception.BadRequestException;
import com.dailyhealthreminder.exception.ResourceNotFoundException;
import com.dailyhealthreminder.exception.UnauthorizedException;
import com.dailyhealthreminder.repository.ReminderRepository;
import com.dailyhealthreminder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for reminder operations.
 * Handles reminder-related business logic.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;

    /**
     * Create a new reminder for a user.
     * 
     * @param email User email
     * @param request Reminder request
     * @return Created reminder response
     */
    @Transactional
    public ReminderResponse createReminder(String email, ReminderRequest request) {
        log.info("Creating reminder for user: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        // Validate request
        validateReminderRequest(request);

        Reminder reminder = Reminder.builder()
                .user(user)
                .title(request.getTitle())
                .description(request.getDescription())
                .reminderType(request.getReminderType())
                .reminderTime(request.getReminderTime())
                .frequency(request.getFrequency())
                .daysOfWeek(request.getDaysOfWeek())
                .dayOfMonth(request.getDayOfMonth())
                .startDate(request.getStartDate() != null ? request.getStartDate() : LocalDateTime.now())
                .endDate(request.getEndDate())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .notes(request.getNotes())
                .build();

        Reminder savedReminder = reminderRepository.save(reminder);
        log.info("Reminder created successfully with ID: {}", savedReminder.getId());

        return mapToReminderResponse(savedReminder);
    }

    /**
     * Get all reminders for a user.
     * 
     * @param email User email
     * @return List of reminder responses
     */
    @Transactional(readOnly = true)
    public List<ReminderResponse> getUserReminders(String email) {
        log.info("Getting reminders for user: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        List<Reminder> reminders = reminderRepository.findByUserOrderByReminderTimeAsc(user);

        return reminders.stream()
                .map(this::mapToReminderResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get active reminders for a user.
     * 
     * @param email User email
     * @return List of active reminder responses
     */
    @Transactional(readOnly = true)
    public List<ReminderResponse> getActiveReminders(String email) {
        log.info("Getting active reminders for user: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        List<Reminder> reminders = reminderRepository.findByUserAndIsActive(user, true);

        return reminders.stream()
                .map(this::mapToReminderResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get reminders by type.
     * 
     * @param email User email
     * @param reminderType Reminder type
     * @return List of reminder responses
     */
    @Transactional(readOnly = true)
    public List<ReminderResponse> getRemindersByType(String email, String reminderType) {
        log.info("Getting reminders by type {} for user: {}", reminderType, email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        List<Reminder> reminders = reminderRepository.findByUserAndReminderType(user, reminderType);

        return reminders.stream()
                .map(this::mapToReminderResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get a specific reminder by ID.
     * 
     * @param email User email
     * @param reminderId Reminder ID
     * @return Reminder response
     */
    @Transactional(readOnly = true)
    public ReminderResponse getReminderById(String email, String reminderId) {
        log.info("Getting reminder with ID: {} for user: {}", reminderId, email);

        Reminder reminder = reminderRepository.findById(reminderId)
                .orElseThrow(() -> new ResourceNotFoundException("Reminder", "id", reminderId));

        // Check if reminder belongs to user
        if (!reminder.getUser().getEmail().equals(email)) {
            throw new UnauthorizedException("You are not authorized to access this reminder");
        }

        return mapToReminderResponse(reminder);
    }

    /**
     * Update a reminder.
     * 
     * @param email User email
     * @param reminderId Reminder ID
     * @param request Reminder request
     * @return Updated reminder response
     */
    @Transactional
    public ReminderResponse updateReminder(String email, String reminderId, ReminderRequest request) {
        log.info("Updating reminder with ID: {} for user: {}", reminderId, email);

        Reminder reminder = reminderRepository.findById(reminderId)
                .orElseThrow(() -> new ResourceNotFoundException("Reminder", "id", reminderId));

        // Check if reminder belongs to user
        if (!reminder.getUser().getEmail().equals(email)) {
            throw new UnauthorizedException("You are not authorized to update this reminder");
        }

        // Validate request
        validateReminderRequest(request);

        // Update fields
        if (request.getTitle() != null) {
            reminder.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            reminder.setDescription(request.getDescription());
        }
        if (request.getReminderType() != null) {
            reminder.setReminderType(request.getReminderType());
        }
        if (request.getReminderTime() != null) {
            reminder.setReminderTime(request.getReminderTime());
        }
        if (request.getFrequency() != null) {
            reminder.setFrequency(request.getFrequency());
        }
        if (request.getDaysOfWeek() != null) {
            reminder.setDaysOfWeek(request.getDaysOfWeek());
        }
        if (request.getDayOfMonth() != null) {
            reminder.setDayOfMonth(request.getDayOfMonth());
        }
        if (request.getStartDate() != null) {
            reminder.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            reminder.setEndDate(request.getEndDate());
        }
        if (request.getIsActive() != null) {
            reminder.setActive(request.getIsActive());
        }
        if (request.getNotes() != null) {
            reminder.setNotes(request.getNotes());
        }

        Reminder updatedReminder = reminderRepository.save(reminder);
        log.info("Reminder updated successfully with ID: {}", updatedReminder.getId());

        return mapToReminderResponse(updatedReminder);
    }

    /**
     * Toggle reminder active status.
     * 
     * @param email User email
     * @param reminderId Reminder ID
     * @return Updated reminder response
     */
    @Transactional
    public ReminderResponse toggleReminderStatus(String email, String reminderId) {
        log.info("Toggling reminder status with ID: {} for user: {}", reminderId, email);

        Reminder reminder = reminderRepository.findById(reminderId)
                .orElseThrow(() -> new ResourceNotFoundException("Reminder", "id", reminderId));

        // Check if reminder belongs to user
        if (!reminder.getUser().getEmail().equals(email)) {
            throw new UnauthorizedException("You are not authorized to update this reminder");
        }

        reminder.setActive(!reminder.isActive());
        Reminder updatedReminder = reminderRepository.save(reminder);

        log.info("Reminder status toggled to: {}", updatedReminder.isActive());
        return mapToReminderResponse(updatedReminder);
    }

    /**
     * Delete a reminder.
     * 
     * @param email User email
     * @param reminderId Reminder ID
     */
    @Transactional
    public void deleteReminder(String email, String reminderId) {
        log.info("Deleting reminder with ID: {} for user: {}", reminderId, email);

        Reminder reminder = reminderRepository.findById(reminderId)
                .orElseThrow(() -> new ResourceNotFoundException("Reminder", "id", reminderId));

        // Check if reminder belongs to user
        if (!reminder.getUser().getEmail().equals(email)) {
            throw new UnauthorizedException("You are not authorized to delete this reminder");
        }

        reminderRepository.delete(reminder);
        log.info("Reminder deleted successfully with ID: {}", reminderId);
    }

    /**
     * Get reminder count for a user.
     * 
     * @param email User email
     * @return Reminder count
     */
    @Transactional(readOnly = true)
    public long getReminderCount(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        return reminderRepository.countByUser(user);
    }

    /**
     * Validate reminder request.
     * 
     * @param request Reminder request
     */
    private void validateReminderRequest(ReminderRequest request) {
        if (request.getFrequency().equals("WEEKLY") && 
            (request.getDaysOfWeek() == null || request.getDaysOfWeek().isEmpty())) {
            throw new BadRequestException("Days of week are required for weekly reminders");
        }

        if (request.getFrequency().equals("MONTHLY") && request.getDayOfMonth() == null) {
            throw new BadRequestException("Day of month is required for monthly reminders");
        }

        if (request.getEndDate() != null && request.getStartDate() != null &&
            request.getEndDate().isBefore(request.getStartDate())) {
            throw new BadRequestException("End date cannot be before start date");
        }
    }

    /**
     * Map Reminder entity to ReminderResponse DTO.
     * 
     * @param reminder Reminder entity
     * @return ReminderResponse DTO
     */
    private ReminderResponse mapToReminderResponse(Reminder reminder) {
        return ReminderResponse.builder()
                .id(reminder.getId())
                .userId(reminder.getUser().getId())
                .title(reminder.getTitle())
                .description(reminder.getDescription())
                .reminderType(reminder.getReminderType())
                .reminderTime(reminder.getReminderTime())
                .frequency(reminder.getFrequency())
                .daysOfWeek(reminder.getDaysOfWeek())
                .dayOfMonth(reminder.getDayOfMonth())
                .startDate(reminder.getStartDate())
                .endDate(reminder.getEndDate())
                .isActive(reminder.isActive())
                .notes(reminder.getNotes())
                .createdAt(reminder.getCreatedAt())
                .updatedAt(reminder.getUpdatedAt())
                .build();
    }
}
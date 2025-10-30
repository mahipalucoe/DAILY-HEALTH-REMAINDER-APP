package com.dailyhealthreminder.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * DTO for reminder creation and update requests.
 * Contains all information needed to create or update a reminder.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReminderRequest {

    /**
     * Title of the reminder.
     */
    @NotBlank(message = "Title is required")
    private String title;

    /**
     * Description of the reminder.
     */
    private String description;

    /**
     * Type of reminder (e.g., MEDICATION, EXERCISE, WATER).
     */
    @NotBlank(message = "Reminder type is required")
    private String reminderType;

    /**
     * Time when the reminder should trigger.
     */
    @NotNull(message = "Reminder time is required")
    private LocalTime reminderTime;

    /**
     * Frequency of the reminder (DAILY, WEEKLY, MONTHLY).
     */
    @NotBlank(message = "Frequency is required")
    private String frequency;

    /**
     * Days of the week for weekly reminders (1-7).
     */
    private List<Integer> daysOfWeek;

    /**
     * Day of the month for monthly reminders (1-31).
     */
    private Integer dayOfMonth;

    /**
     * Start date for the reminder.
     */
    private LocalDateTime startDate;

    /**
     * End date for the reminder (optional).
     */
    private LocalDateTime endDate;

    /**
     * Flag indicating if the reminder is active.
     */
    private Boolean isActive;

    /**
     * Custom notes for the reminder.
     */
    private String notes;
}
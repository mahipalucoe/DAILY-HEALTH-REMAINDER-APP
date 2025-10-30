package com.dailyhealthreminder.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * DTO for reminder response.
 * Contains reminder information for API responses.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReminderResponse {

    /**
     * Reminder's unique identifier.
     */
    private String id;

    /**
     * User ID who owns this reminder.
     */
    private String userId;

    /**
     * Title of the reminder.
     */
    private String title;

    /**
     * Description of the reminder.
     */
    private String description;

    /**
     * Type of reminder.
     */
    private String reminderType;

    /**
     * Time when the reminder should trigger.
     */
    private LocalTime reminderTime;

    /**
     * Frequency of the reminder.
     */
    private String frequency;

    /**
     * Days of the week for weekly reminders.
     */
    private List<Integer> daysOfWeek;

    /**
     * Day of the month for monthly reminders.
     */
    private Integer dayOfMonth;

    /**
     * Start date for the reminder.
     */
    private LocalDateTime startDate;

    /**
     * End date for the reminder.
     */
    private LocalDateTime endDate;

    /**
     * Flag indicating if the reminder is active.
     */
    private boolean isActive;

    /**
     * Custom notes for the reminder.
     */
    private String notes;

    /**
     * Timestamp when the reminder was created.
     */
    private LocalDateTime createdAt;

    /**
     * Timestamp when the reminder was last updated.
     */
    private LocalDateTime updatedAt;
}
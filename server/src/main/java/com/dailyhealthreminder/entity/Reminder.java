package com.dailyhealthreminder.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Entity class representing a health reminder.
 * Users can create reminders for various health-related activities.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "reminders")
public class Reminder {

    /**
     * Unique identifier for the reminder.
     */
    @Id
    private String id;

    /**
     * Reference to the user who owns this reminder.
     */
    @DBRef
    private User user;

    /**
     * Title of the reminder.
     */
    private String title;

    /**
     * Detailed description of the reminder.
     */
    private String description;

    /**
     * Type of reminder (e.g., MEDICATION, EXERCISE, WATER).
     */
    private String reminderType;

    /**
     * Time when the reminder should trigger.
     */
    private LocalTime reminderTime;

    /**
     * Frequency of the reminder (DAILY, WEEKLY, MONTHLY).
     */
    private String frequency;

    /**
     * Days of the week for weekly reminders (1-7, where 1 is Monday).
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
    @Builder.Default
    private boolean isActive = true;

    /**
     * Custom notes for the reminder.
     */
    private String notes;

    /**
     * Timestamp when the reminder was created.
     */
    @CreatedDate
    private LocalDateTime createdAt;

    /**
     * Timestamp when the reminder was last updated.
     */
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
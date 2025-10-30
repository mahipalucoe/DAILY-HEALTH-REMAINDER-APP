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
import java.util.Map;

/**
 * Entity class representing a health log entry.
 * Users can log various health metrics and activities.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "health_logs")
public class HealthLog {

    /**
     * Unique identifier for the health log.
     */
    @Id
    private String id;

    /**
     * Reference to the user who owns this log.
     */
    @DBRef
    private User user;

    /**
     * Type of health log (WEIGHT, EXERCISE, SLEEP, WATER, MOOD, MEDICATION).
     */
    private String logType;

    /**
     * Date and time of the logged activity.
     */
    private LocalDateTime logDate;

    /**
     * Primary value for the log (e.g., weight in kg, sleep hours).
     */
    private Double value;

    /**
     * Unit of measurement (e.g., kg, hours, glasses, minutes).
     */
    private String unit;

    /**
     * Additional metadata for the log entry.
     * Can store exercise type, mood rating, medication name, etc.
     */
    private Map<String, Object> metadata;

    /**
     * Notes or comments about the log entry.
     */
    private String notes;

    /**
     * Duration in minutes (for activities like exercise).
     */
    private Integer durationMinutes;

    /**
     * Calories burned (for exercise logs).
     */
    private Integer caloriesBurned;

    /**
     * Quality rating (1-10) for sleep, mood, etc.
     */
    private Integer qualityRating;

    /**
     * Timestamp when the log was created.
     */
    @CreatedDate
    private LocalDateTime createdAt;

    /**
     * Timestamp when the log was last updated.
     */
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
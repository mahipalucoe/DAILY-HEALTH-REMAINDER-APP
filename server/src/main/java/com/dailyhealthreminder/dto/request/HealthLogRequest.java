package com.dailyhealthreminder.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO for health log creation and update requests.
 * Contains all information needed to create or update a health log entry.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthLogRequest {

    /**
     * Type of health log (WEIGHT, EXERCISE, SLEEP, WATER, MOOD, MEDICATION).
     */
    @NotBlank(message = "Log type is required")
    private String logType;

    /**
     * Date and time of the logged activity.
     */
    @NotNull(message = "Log date is required")
    private LocalDateTime logDate;

    /**
     * Primary value for the log.
     */
    private Double value;

    /**
     * Unit of measurement.
     */
    private String unit;

    /**
     * Additional metadata for the log entry.
     */
    private Map<String, Object> metadata;

    /**
     * Notes or comments about the log entry.
     */
    private String notes;

    /**
     * Duration in minutes (for activities).
     */
    private Integer durationMinutes;

    /**
     * Calories burned (for exercise logs).
     */
    private Integer caloriesBurned;

    /**
     * Quality rating (1-10).
     */
    private Integer qualityRating;
}
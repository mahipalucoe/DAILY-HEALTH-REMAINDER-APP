package com.dailyhealthreminder.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO for health log response.
 * Contains health log information for API responses.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HealthLogResponse {

    /**
     * Health log's unique identifier.
     */
    private String id;

    /**
     * User ID who owns this log.
     */
    private String userId;

    /**
     * Type of health log.
     */
    private String logType;

    /**
     * Date and time of the logged activity.
     */
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
     * Additional metadata.
     */
    private Map<String, Object> metadata;

    /**
     * Notes or comments.
     */
    private String notes;

    /**
     * Duration in minutes.
     */
    private Integer durationMinutes;

    /**
     * Calories burned.
     */
    private Integer caloriesBurned;

    /**
     * Quality rating.
     */
    private Integer qualityRating;

    /**
     * Timestamp when the log was created.
     */
    private LocalDateTime createdAt;

    /**
     * Timestamp when the log was last updated.
     */
    private LocalDateTime updatedAt;
}
package com.dailyhealthreminder.repository;

import com.dailyhealthreminder.entity.HealthLog;
import com.dailyhealthreminder.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for HealthLog entity.
 * Provides database operations for health logs.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@Repository
public interface HealthLogRepository extends MongoRepository<HealthLog, String> {

    /**
     * Find all health logs for a specific user.
     * 
     * @param user User entity
     * @param pageable Pagination information
     * @return Page of health logs
     */
    Page<HealthLog> findByUser(User user, Pageable pageable);

    /**
     * Find health logs for a user ordered by log date descending.
     * 
     * @param user User entity
     * @param pageable Pagination information
     * @return Page of health logs
     */
    Page<HealthLog> findByUserOrderByLogDateDesc(User user, Pageable pageable);

    /**
     * Find health logs by user and log type.
     * 
     * @param user User entity
     * @param logType Type of log
     * @param pageable Pagination information
     * @return Page of health logs
     */
    Page<HealthLog> findByUserAndLogType(User user, String logType, Pageable pageable);

    /**
     * Find health logs by user within a date range.
     * 
     * @param user User entity
     * @param startDate Start date
     * @param endDate End date
     * @param pageable Pagination information
     * @return Page of health logs
     */
    Page<HealthLog> findByUserAndLogDateBetween(
            User user, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Find health logs by user, log type, and date range.
     * 
     * @param user User entity
     * @param logType Type of log
     * @param startDate Start date
     * @param endDate End date
     * @return List of health logs
     */
    List<HealthLog> findByUserAndLogTypeAndLogDateBetween(
            User user, String logType, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Count health logs for a specific user.
     * 
     * @param user User entity
     * @return Count of health logs
     */
    long countByUser(User user);

    /**
     * Count health logs by user and log type.
     * 
     * @param user User entity
     * @param logType Type of log
     * @return Count of health logs
     */
    long countByUserAndLogType(User user, String logType);

    /**
     * Delete all health logs for a specific user.
     * 
     * @param user User entity
     */
    void deleteByUser(User user);
}
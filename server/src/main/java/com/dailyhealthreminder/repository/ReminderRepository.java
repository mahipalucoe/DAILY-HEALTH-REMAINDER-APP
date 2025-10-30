package com.dailyhealthreminder.repository;

import com.dailyhealthreminder.entity.Reminder;
import com.dailyhealthreminder.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for Reminder entity.
 * Provides database operations for reminders.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@Repository
public interface ReminderRepository extends MongoRepository<Reminder, String> {

    /**
     * Find all reminders for a specific user.
     * 
     * @param user User entity
     * @return List of reminders
     */
    List<Reminder> findByUser(User user);

    /**
     * Find all reminders for a user ordered by reminder time.
     * 
     * @param user User entity
     * @return List of reminders ordered by time
     */
    List<Reminder> findByUserOrderByReminderTimeAsc(User user);

    /**
     * Find active reminders for a specific user.
     * 
     * @param user User entity
     * @param isActive Active status
     * @return List of active reminders
     */
    List<Reminder> findByUserAndIsActive(User user, boolean isActive);

    /**
     * Find reminders by user and reminder type.
     * 
     * @param user User entity
     * @param reminderType Type of reminder
     * @return List of reminders
     */
    List<Reminder> findByUserAndReminderType(User user, String reminderType);

    /**
     * Find reminders by user and frequency.
     * 
     * @param user User entity
     * @param frequency Frequency of reminder
     * @return List of reminders
     */
    List<Reminder> findByUserAndFrequency(User user, String frequency);

    /**
     * Find active reminders for a user within a date range.
     * 
     * @param user User entity
     * @param isActive Active status
     * @param startDate Start date
     * @param endDate End date
     * @return List of reminders
     */
    List<Reminder> findByUserAndIsActiveAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            User user, boolean isActive, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Count reminders for a specific user.
     * 
     * @param user User entity
     * @return Count of reminders
     */
    long countByUser(User user);
}
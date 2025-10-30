package com.dailyhealthreminder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * Main application class for Daily Health Reminder Backend.
 * This class bootstraps the Spring Boot application.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@SpringBootApplication
@EnableMongoAuditing
public class DailyHealthReminderApplication {

    /**
     * Main method to start the Spring Boot application.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(DailyHealthReminderApplication.class, args);
    }
}
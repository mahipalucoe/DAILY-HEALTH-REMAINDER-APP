package com.dailyhealthreminder;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Main application test class.
 * Tests the Spring Boot application context loading.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@SpringBootTest
@ActiveProfiles("dev")
class DailyHealthReminderApplicationTests {

    /**
     * Test that the application context loads successfully.
     */
    @Test
    void contextLoads() {
        // This test will fail if the application context cannot start
    }

    /**
     * Test that the application starts successfully.
     */
    @Test
    void applicationStarts() {
        DailyHealthReminderApplication.main(new String[] {});
    }
}
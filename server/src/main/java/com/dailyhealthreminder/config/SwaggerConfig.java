package com.dailyhealthreminder.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/OpenAPI configuration class.
 * Configures API documentation with Swagger UI.
 * 
 * @author Daily Health Reminder Team
 * @version 1.0
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Daily Health Reminder API",
                version = "1.0.0",
                description = "RESTful API for Daily Health Reminder application. " +
                        "This API allows users to manage health logs, set reminders, and track their daily health activities.",
                contact = @Contact(
                        name = "Daily Health Reminder Team",
                        email = "support@dailyhealthreminder.com",
                        url = "https://dailyhealthreminder.com"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(
                        description = "Local Development Server",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Production Server",
                        url = "https://api.dailyhealthreminder.com"
                )
        }
)
@SecurityScheme(
        name = "Bearer Authentication",
        description = "JWT authentication using Bearer token. " +
                "Please login to get your token and use it in the format: Bearer {token}",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
    // Configuration is handled through annotations
}
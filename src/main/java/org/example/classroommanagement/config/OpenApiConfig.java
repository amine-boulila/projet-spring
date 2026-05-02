package org.example.classroommanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Swagger/OpenAPI documentation.
 * Provides API metadata and documentation settings.
 * 
 * Validates Requirements: 13.1, 13.2, 13.3, 13.4, 13.5, 13.6
 */
@Configuration
public class OpenApiConfig {
    
    /**
     * Creates and configures the OpenAPI bean for Swagger documentation.
     * 
     * @return configured OpenAPI instance with API metadata
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Classroom Management API")
                        .version("1.0")
                        .description("REST API for managing university courses, classes, and users"));
    }
}

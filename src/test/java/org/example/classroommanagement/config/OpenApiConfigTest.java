package org.example.classroommanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for OpenApiConfig configuration class.
 * Verifies that Swagger/OpenAPI is correctly configured.
 */
class OpenApiConfigTest {
    
    private final OpenApiConfig openApiConfig = new OpenApiConfig();
    
    @Test
    void customOpenAPI_ShouldReturnConfiguredOpenAPI() {
        // When
        OpenAPI openAPI = openApiConfig.customOpenAPI();
        
        // Then
        assertThat(openAPI).isNotNull();
        assertThat(openAPI.getInfo()).isNotNull();
    }
    
    @Test
    void customOpenAPI_ShouldHaveCorrectTitle() {
        // When
        OpenAPI openAPI = openApiConfig.customOpenAPI();
        
        // Then
        assertThat(openAPI.getInfo().getTitle()).isEqualTo("Classroom Management API");
    }
    
    @Test
    void customOpenAPI_ShouldHaveCorrectVersion() {
        // When
        OpenAPI openAPI = openApiConfig.customOpenAPI();
        
        // Then
        assertThat(openAPI.getInfo().getVersion()).isEqualTo("1.0");
    }
    
    @Test
    void customOpenAPI_ShouldHaveCorrectDescription() {
        // When
        OpenAPI openAPI = openApiConfig.customOpenAPI();
        
        // Then
        assertThat(openAPI.getInfo().getDescription())
            .isEqualTo("REST API for managing university courses, classes, and users");
    }
}

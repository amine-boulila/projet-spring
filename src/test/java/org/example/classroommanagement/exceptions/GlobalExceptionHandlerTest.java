package org.example.classroommanagement.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Unit tests for GlobalExceptionHandler.
 * Verifies that exception handlers return correct HTTP status codes and error responses.
 * 
 * Validates Requirements: 3.9, 4.4, 4.5, 6.4
 */
class GlobalExceptionHandlerTest {
    
    private GlobalExceptionHandler exceptionHandler;
    
    @Mock
    private HttpServletRequest request;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        exceptionHandler = new GlobalExceptionHandler();
        when(request.getRequestURI()).thenReturn("/api/classroom/test");
    }
    
    /**
     * Test that EntityNotFoundException returns 404 Not Found.
     * 
     * Validates Requirement: 3.9
     */
    @Test
    void handleEntityNotFound_ShouldReturn404() {
        // Arrange
        EntityNotFoundException exception = new EntityNotFoundException("Entity not found");
        
        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleEntityNotFound(exception, request);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(404);
        assertThat(response.getBody().getError()).isEqualTo("Not Found");
        assertThat(response.getBody().getMessage()).isEqualTo("Entity not found");
        assertThat(response.getBody().getPath()).isEqualTo("/api/classroom/test");
        assertThat(response.getBody().getTimestamp()).isNotNull();
    }
    
    /**
     * Test that IllegalArgumentException returns 400 Bad Request.
     * 
     * Validates Requirements: 4.4, 4.5
     */
    @Test
    void handleIllegalArgument_ShouldReturn400() {
        // Arrange
        IllegalArgumentException exception = new IllegalArgumentException("Invalid input");
        
        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleIllegalArgument(exception, request);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(400);
        assertThat(response.getBody().getError()).isEqualTo("Bad Request");
        assertThat(response.getBody().getMessage()).isEqualTo("Invalid input");
        assertThat(response.getBody().getPath()).isEqualTo("/api/classroom/test");
        assertThat(response.getBody().getTimestamp()).isNotNull();
    }
    
    /**
     * Test that DataIntegrityViolationException returns 409 Conflict.
     * 
     * Validates Requirement: 6.4
     */
    @Test
    void handleDataIntegrityViolation_ShouldReturn409() {
        // Arrange
        DataIntegrityViolationException exception = new DataIntegrityViolationException("Constraint violation");
        
        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleDataIntegrityViolation(exception, request);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(409);
        assertThat(response.getBody().getError()).isEqualTo("Conflict");
        assertThat(response.getBody().getMessage()).contains("Data integrity violation");
        assertThat(response.getBody().getPath()).isEqualTo("/api/classroom/test");
        assertThat(response.getBody().getTimestamp()).isNotNull();
    }
    
    /**
     * Test that generic Exception returns 500 Internal Server Error.
     * 
     * Validates Requirements: 3.9, 4.4, 4.5, 6.4
     */
    @Test
    void handleGenericException_ShouldReturn500() {
        // Arrange
        Exception exception = new Exception("Unexpected error");
        
        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleGenericException(exception, request);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(500);
        assertThat(response.getBody().getError()).isEqualTo("Internal Server Error");
        assertThat(response.getBody().getMessage()).isEqualTo("An unexpected error occurred. Please contact support.");
        assertThat(response.getBody().getPath()).isEqualTo("/api/classroom/test");
        assertThat(response.getBody().getTimestamp()).isNotNull();
    }
    
    /**
     * Test that ErrorResponse timestamp is set automatically.
     */
    @Test
    void errorResponse_ShouldHaveTimestamp() {
        // Act
        ErrorResponse errorResponse = new ErrorResponse(404, "Not Found", "Test message", "/test/path");
        
        // Assert
        assertThat(errorResponse.getTimestamp()).isNotNull();
    }
    
    /**
     * Test that ErrorResponse default constructor sets timestamp.
     */
    @Test
    void errorResponse_DefaultConstructor_ShouldSetTimestamp() {
        // Act
        ErrorResponse errorResponse = new ErrorResponse();
        
        // Assert
        assertThat(errorResponse.getTimestamp()).isNotNull();
    }
    
    /**
     * Test that ErrorResponse setters work correctly.
     */
    @Test
    void errorResponse_Setters_ShouldWork() {
        // Arrange
        ErrorResponse errorResponse = new ErrorResponse();
        
        // Act
        errorResponse.setStatus(404);
        errorResponse.setError("Not Found");
        errorResponse.setMessage("Test message");
        errorResponse.setPath("/test/path");
        
        // Assert
        assertThat(errorResponse.getStatus()).isEqualTo(404);
        assertThat(errorResponse.getError()).isEqualTo("Not Found");
        assertThat(errorResponse.getMessage()).isEqualTo("Test message");
        assertThat(errorResponse.getPath()).isEqualTo("/test/path");
    }
}

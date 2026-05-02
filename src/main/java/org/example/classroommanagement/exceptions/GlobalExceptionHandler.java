package org.example.classroommanagement.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for centralized error handling across all REST endpoints.
 * Returns consistent error responses with appropriate HTTP status codes.
 * 
 * Validates Requirements: 3.9, 4.4, 4.5, 6.4
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    /**
     * Handles EntityNotFoundException and returns 404 Not Found.
     * Thrown when a requested entity (user, class, or course) does not exist.
     * 
     * @param ex the EntityNotFoundException
     * @param request the HTTP request
     * @return ResponseEntity with ErrorResponse and 404 status
     * 
     * Validates Requirements: 3.9, 4.4, 4.5, 6.4
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(
            EntityNotFoundException ex,
            HttpServletRequest request) {
        
        logger.warn("Entity not found: {} - Path: {}", ex.getMessage(), request.getRequestURI());
        
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    
    /**
     * Handles IllegalArgumentException and returns 400 Bad Request.
     * Thrown when validation fails (e.g., empty fields, invalid values).
     * 
     * @param ex the IllegalArgumentException
     * @param request the HTTP request
     * @return ResponseEntity with ErrorResponse and 400 status
     * 
     * Validates Requirements: 3.9, 4.4, 4.5, 6.4
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request) {
        
        logger.warn("Validation error: {} - Path: {}", ex.getMessage(), request.getRequestURI());
        
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    
    /**
     * Handles DataIntegrityViolationException and returns 409 Conflict.
     * Thrown when database constraints are violated (e.g., duplicate keys, foreign key violations).
     * 
     * @param ex the DataIntegrityViolationException
     * @param request the HTTP request
     * @return ResponseEntity with ErrorResponse and 409 status
     * 
     * Validates Requirements: 3.9, 4.4, 4.5, 6.4
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {
        
        logger.error("Data integrity violation: {} - Path: {}", ex.getMessage(), request.getRequestURI());
        
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                "Data integrity violation: " + ex.getMostSpecificCause().getMessage(),
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
    
    /**
     * Handles all other exceptions and returns 500 Internal Server Error.
     * Logs the full stack trace for debugging purposes.
     * 
     * @param ex the Exception
     * @param request the HTTP request
     * @return ResponseEntity with ErrorResponse and 500 status
     * 
     * Validates Requirements: 3.9, 4.4, 4.5, 6.4
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {
        
        logger.error("Unexpected error occurred - Path: {}", request.getRequestURI(), ex);
        
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred. Please contact support.",
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

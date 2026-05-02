package org.example.classroommanagement.exceptions;

import java.time.LocalDateTime;

/**
 * Error response model for consistent error handling across REST endpoints.
 * Contains timestamp, HTTP status, error type, message, and request path.
 * 
 * Validates Requirements: 3.9, 4.4, 4.5, 6.4
 */
public class ErrorResponse {
    
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    
    /**
     * Default constructor.
     */
    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }
    
    /**
     * Full constructor for creating error responses.
     * 
     * @param status HTTP status code
     * @param error error type/name
     * @param message detailed error message
     * @param path request path that caused the error
     */
    public ErrorResponse(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
    
    /**
     * Gets the timestamp when the error occurred.
     * 
     * @return the timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    /**
     * Sets the timestamp when the error occurred.
     * 
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    /**
     * Gets the HTTP status code.
     * 
     * @return the status code
     */
    public int getStatus() {
        return status;
    }
    
    /**
     * Sets the HTTP status code.
     * 
     * @param status the status code to set
     */
    public void setStatus(int status) {
        this.status = status;
    }
    
    /**
     * Gets the error type/name.
     * 
     * @return the error type
     */
    public String getError() {
        return error;
    }
    
    /**
     * Sets the error type/name.
     * 
     * @param error the error type to set
     */
    public void setError(String error) {
        this.error = error;
    }
    
    /**
     * Gets the detailed error message.
     * 
     * @return the error message
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * Sets the detailed error message.
     * 
     * @param message the error message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * Gets the request path that caused the error.
     * 
     * @return the request path
     */
    public String getPath() {
        return path;
    }
    
    /**
     * Sets the request path that caused the error.
     * 
     * @param path the request path to set
     */
    public void setPath(String path) {
        this.path = path;
    }
}

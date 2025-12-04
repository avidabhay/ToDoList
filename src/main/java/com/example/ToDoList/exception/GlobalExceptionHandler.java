package com.example.ToDoList.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Global exception handler for the entire application.
 * 
 * <p>
 * This class uses @ControllerAdvice to handle exceptions thrown by any
 * controller
 * in the application. It provides centralized exception handling and ensures
 * consistent error response format across all API endpoints.
 * </p>
 * 
 * <p>
 * All error responses follow this structure:
 * </p>
 * 
 * <pre>
 * {
 *   "timestamp": "2025-12-04T18:45:00",
 *   "status": 404,
 *   "error": "Not Found",
 *   "message": "Todo not found with id: 123",
 *   "path": "/api/todos/123"
 * }
 * </pre>
 * 
 * @author Abhay (avidAbhay)
 * @version 1.0
 * @since 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Logger for recording exception details.
     * Helps with debugging and monitoring in production.
     */
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles TodoNotFoundException and returns a 404 Not Found response.
     * 
     * <p>
     * This method is invoked whenever a TodoNotFoundException is thrown
     * anywhere in the application. It logs the error and returns a properly
     * formatted error response to the client.
     * </p>
     * 
     * @param ex      the TodoNotFoundException that was thrown
     * @param request the web request that caused the exception
     * @return ResponseEntity with 404 status and error details
     */
    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<Object> handleTodoNotFoundException(
            TodoNotFoundException ex,
            WebRequest request) {

        // Log the error for monitoring and debugging
        logger.warn("Todo not found: {}", ex.getMessage());

        // Build structured error response
        Map<String, Object> errorResponse = buildErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles all uncaught exceptions and returns a 500 Internal Server Error
     * response.
     * 
     * <p>
     * This is a catch-all handler for any exception not specifically handled
     * by other @ExceptionHandler methods. It prevents stack traces from being
     * exposed to clients and ensures a consistent error response format.
     * </p>
     * 
     * <p>
     * Important: This logs the full stack trace for debugging while only
     * returning a generic error message to the client for security.
     * </p>
     * 
     * @param ex      the exception that was thrown
     * @param request the web request that caused the exception
     * @return ResponseEntity with 500 status and error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(
            Exception ex,
            WebRequest request) {

        // Log the full exception with stack trace for debugging
        logger.error("Unexpected error occurred: ", ex);

        // Build structured error response
        // Note: We use a generic message to avoid exposing internal details
        Map<String, Object> errorResponse = buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please try again later.",
                request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Builds a standardized error response map.
     * 
     * <p>
     * This helper method ensures all error responses have the same structure,
     * making it easier for clients to parse and handle errors consistently.
     * </p>
     * 
     * @param status  the HTTP status code
     * @param message the error message to display
     * @param path    the request path that caused the error
     * @return a map containing the error response fields
     */
    private Map<String, Object> buildErrorResponse(HttpStatus status, String message, String path) {
        Map<String, Object> errorResponse = new LinkedHashMap<>();

        // Timestamp when the error occurred
        errorResponse.put("timestamp", LocalDateTime.now());

        // HTTP status code (e.g., 404, 500)
        errorResponse.put("status", status.value());

        // HTTP status reason phrase (e.g., "Not Found", "Internal Server Error")
        errorResponse.put("error", status.getReasonPhrase());

        // Detailed error message
        errorResponse.put("message", message);

        // Request path that caused the error
        errorResponse.put("path", path);

        return errorResponse;
    }

}

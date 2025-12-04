package com.example.ToDoList.exception;

/**
 * Custom exception thrown when a requested Todo item is not found in the
 * database.
 * 
 * <p>
 * This exception extends RuntimeException, making it an unchecked exception
 * that doesn't need to be explicitly declared in method signatures.
 * </p>
 * 
 * <p>
 * It is typically thrown by the service layer when attempting to retrieve
 * a todo by ID that doesn't exist, and is caught by the GlobalExceptionHandler
 * to return a proper HTTP 404 response to the client.
 * </p>
 * 
 * <p>
 * Example usage:
 * </p>
 * 
 * <pre>
 * Todo todo = todoRepository.findById(id)
 *         .orElseThrow(() -&gt; new TodoNotFoundException(id));
 * </pre>
 * 
 * @author Abhay (avidAbhay)
 * @version 1.0
 * @since 1.0
 * @see com.example.ToDoList.exception.GlobalExceptionHandler
 */
public class TodoNotFoundException extends RuntimeException {

    /**
     * The ID of the todo that was not found.
     * Stored for potential logging or debugging purposes.
     */
    private final Long todoId;

    /**
     * Constructs a new TodoNotFoundException with the specified todo ID.
     * 
     * <p>
     * The exception message is automatically formatted to include the ID
     * for better error reporting and debugging.
     * </p>
     * 
     * @param id the ID of the todo that was not found
     */
    public TodoNotFoundException(Long id) {
        super(String.format("Todo not found with id: %d", id));
        this.todoId = id;
    }

    /**
     * Gets the ID of the todo that was not found.
     * 
     * <p>
     * This can be useful for logging or additional error handling.
     * </p>
     * 
     * @return the todo ID that caused this exception
     */
    public Long getTodoId() {
        return todoId;
    }

}

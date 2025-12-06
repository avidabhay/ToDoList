package com.example.ToDoList.exception;

/**
 * Exception thrown when a todo is not found.
 *
 * @author Abhay (avidAbhay)
 */
public class TodoNotFoundException extends RuntimeException {

    private final Long todoId;

    public TodoNotFoundException(Long id) {
        super(String.format("Todo not found with id: %d", id));
        this.todoId = id;
    }

    public Long getTodoId() {
        return todoId;
    }
}

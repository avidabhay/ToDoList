package com.example.ToDoList.dto;

/**
 * Data Transfer Object for updating existing Todo items.
 * 
 * <p>
 * This DTO is used when clients send PUT requests to update todos.
 * All fields are optional - only the fields that are provided will be updated.
 * Fields set to null will not modify the existing values.
 * </p>
 * 
 * <p>
 * Example JSON request body (partial update):
 * </p>
 * 
 * <pre>
 * {
 *   "title": "Updated title",
 *   "completed": true
 * }
 * </pre>
 * 
 * @author Abhay (avidAbhay)
 * @version 1.0
 * @since 1.0
 */
public class UpdateTodoRequest {

    /** New title for the todo (optional) */
    private String title;

    /** New description for the todo (optional) */
    private String description;

    /** New completion status for the todo (optional) */
    private Boolean completed;

    // ==================== Constructors ====================

    /**
     * Default no-argument constructor.
     * Required for JSON deserialization by Jackson.
     */
    public UpdateTodoRequest() {
    }

    /**
     * Constructor with all fields.
     * 
     * @param title       the new title
     * @param description the new description
     * @param completed   the new completion status
     */
    public UpdateTodoRequest(String title, String description, Boolean completed) {
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    // ==================== Getters and Setters ====================

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

}

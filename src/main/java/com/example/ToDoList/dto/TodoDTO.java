package com.example.ToDoList.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Todo responses.
 * 
 * <p>
 * This DTO is used to transfer todo data from the service layer to the client.
 * It decouples the API response format from the internal entity structure,
 * allowing us to change the database schema without affecting the API contract.
 * </p>
 * 
 * <p>
 * All fields from the Todo entity are included in this DTO to provide
 * complete information to the client.
 * </p>
 * 
 * @author Abhay (avidAbhay)
 * @version 1.0
 * @since 1.0
 * @see com.example.ToDoList.model.Todo
 */
public class TodoDTO {

    /** Unique identifier of the todo */
    private Long id;

    /** Title of the todo */
    private String title;

    /** Detailed description of the todo */
    private String description;

    /** Completion status (true if completed, false otherwise) */
    private Boolean completed;

    /** Timestamp when the todo was created */
    private LocalDateTime createdAt;

    /** Timestamp when the todo was last updated */
    private LocalDateTime updatedAt;

    // ==================== Constructors ====================

    /**
     * Default no-argument constructor.
     * Required for JSON deserialization by Jackson.
     */
    public TodoDTO() {
    }

    /**
     * Full constructor for creating a TodoDTO with all fields.
     * 
     * @param id          the unique identifier
     * @param title       the title of the todo
     * @param description the detailed description
     * @param completed   the completion status
     * @param createdAt   the creation timestamp
     * @param updatedAt   the last update timestamp
     */
    public TodoDTO(Long id, String title, String description, Boolean completed,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // ==================== Getters and Setters ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}

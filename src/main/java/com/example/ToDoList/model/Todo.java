package com.example.ToDoList.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Entity class representing a Todo item in the database.
 * 
 * <p>
 * This class maps to the "todos" table and contains all the information
 * about a single todo item including its title, description, completion status,
 * and automatic timestamps for creation and updates.
 * </p>
 * 
 * <p>
 * The timestamps are automatically managed by Hibernate annotations:
 * - createdAt: Set once when the entity is first persisted
 * - updatedAt: Updated automatically on every modification
 * </p>
 * 
 * @author Abhay (avidAbhay)
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "todos")
public class Todo {

    /**
     * Unique identifier for the todo item.
     * Auto-generated using database identity strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Title of the todo item.
     * This field is required and cannot be null.
     */
    @Column(nullable = false)
    private String title;

    /**
     * Detailed description of the todo item.
     * This field is optional and can store up to 1000 characters.
     */
    @Column(length = 1000)
    private String description;

    /**
     * Completion status of the todo item.
     * Defaults to false (not completed) when a new todo is created.
     */
    @Column(nullable = false)
    private Boolean completed = false;

    /**
     * Timestamp when this todo was created.
     * Automatically set by Hibernate when the entity is first persisted.
     * This field cannot be updated after creation.
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp when this todo was last updated.
     * Automatically updated by Hibernate whenever the entity is modified.
     */
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // ==================== Constructors ====================

    /**
     * Default no-argument constructor required by JPA.
     * Creates a new Todo with default values.
     */
    public Todo() {
    }

    /**
     * Full constructor for creating a Todo with all fields.
     * 
     * @param id          the unique identifier
     * @param title       the title of the todo
     * @param description the detailed description
     * @param completed   the completion status
     * @param createdAt   the creation timestamp
     * @param updatedAt   the last update timestamp
     */
    public Todo(Long id, String title, String description, Boolean completed,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // ==================== Getters and Setters ====================

    /**
     * Gets the unique identifier of this todo.
     * 
     * @return the todo ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of this todo.
     * 
     * @param id the todo ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the title of this todo.
     * 
     * @return the todo title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of this todo.
     * 
     * @param title the title to set (must not be null)
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of this todo.
     * 
     * @return the todo description, or null if not set
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of this todo.
     * 
     * @param description the description to set (can be null)
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Checks if this todo is completed.
     * 
     * @return true if completed, false otherwise
     */
    public Boolean getCompleted() {
        return completed;
    }

    /**
     * Sets the completion status of this todo.
     * 
     * @param completed true to mark as completed, false otherwise
     */
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    /**
     * Gets the timestamp when this todo was created.
     * 
     * @return the creation timestamp
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp.
     * Note: This is typically managed automatically by Hibernate.
     * 
     * @param createdAt the creation timestamp to set
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the timestamp when this todo was last updated.
     * 
     * @return the last update timestamp
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the last update timestamp.
     * Note: This is typically managed automatically by Hibernate.
     * 
     * @param updatedAt the update timestamp to set
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}

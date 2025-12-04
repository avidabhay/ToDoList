package com.example.ToDoList.service;

import com.example.ToDoList.dto.CreateTodoRequest;
import com.example.ToDoList.dto.TodoDTO;
import com.example.ToDoList.dto.UpdateTodoRequest;
import com.example.ToDoList.exception.TodoNotFoundException;
import com.example.ToDoList.model.Todo;
import com.example.ToDoList.repository.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for Todo business logic.
 * 
 * <p>
 * This service handles all business operations related to todos including
 * CRUD operations, filtering, searching, and data transformation between
 * entities and DTOs.
 * </p>
 * 
 * <p>
 * Key responsibilities:
 * </p>
 * <ul>
 * <li>Orchestrate todo operations</li>
 * <li>Apply business rules and validation</li>
 * <li>Convert between Entity and DTO objects</li>
 * <li>Handle filtering and search logic</li>
 * </ul>
 * 
 * @author Abhay (avidAbhay)
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class TodoService {

    private static final Logger logger = LoggerFactory.getLogger(TodoService.class);

    @Autowired
    private TodoRepository todoRepository;

    /**
     * Retrieves todos based on optional filtering criteria.
     * 
     * <p>
     * This is the main entry point for fetching todos with flexible filtering.
     * The method applies filters in priority order:
     * </p>
     * <ol>
     * <li>Search by keyword (if provided) - highest priority</li>
     * <li>Filter by completion status (if provided)</li>
     * <li>Return all todos (default)</li>
     * </ol>
     * 
     * <p>
     * Example usage:
     * </p>
     * 
     * <pre>
     * // Get all todos
     * List&lt;TodoDTO&gt; all = getTodos(null, null);
     * 
     * // Get completed todos only
     * List&lt;TodoDTO&gt; completed = getTodos(true, null);
     * 
     * // Search todos by keyword
     * List&lt;TodoDTO&gt; searched = getTodos(null, "spring");
     * </pre>
     * 
     * @param completed optional filter for completion status (true/false/null)
     * @param search    optional keyword to search in todo titles
     * @return list of todos matching the criteria, never null
     */
    public List<TodoDTO> getTodos(Boolean completed, String search) {
        logger.debug("Fetching todos with filters - completed: {}, search: {}", completed, search);

        // Priority 1: Search by keyword if provided
        if (search != null && !search.isEmpty()) {
            logger.debug("Searching todos by keyword: {}", search);
            return searchTodos(search);
        }

        // Priority 2: Filter by completion status if provided
        if (completed != null) {
            logger.debug("Filtering todos by completion status: {}", completed);
            return getTodosByStatus(completed);
        }

        // Default: Return all todos
        logger.debug("Fetching all todos");
        return getAllTodos();
    }

    /**
     * Retrieves all todos from the database.
     * 
     * @return list of all todos as DTOs, empty list if none exist
     */
    public List<TodoDTO> getAllTodos() {
        logger.info("Fetching all todos");
        List<Todo> todos = todoRepository.findAll();
        logger.debug("Found {} todos", todos.size());

        return todos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a single todo by its ID.
     * 
     * @param id the unique identifier of the todo
     * @return the todo as a DTO
     * @throws TodoNotFoundException if no todo exists with the given ID
     */
    public TodoDTO getTodoById(Long id) {
        logger.info("Fetching todo with id: {}", id);

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Todo not found with id: {}", id);
                    return new TodoNotFoundException(id);
                });

        return convertToDTO(todo);
    }

    /**
     * Creates a new todo from the provided request.
     * 
     * <p>
     * The new todo is automatically set to incomplete (completed = false).
     * Creation and update timestamps are automatically managed by JPA.
     * </p>
     * 
     * @param request the creation request containing title and description
     * @return the created todo as a DTO
     * @throws IllegalArgumentException if title is null or empty
     */
    public TodoDTO createTodo(CreateTodoRequest request) {
        logger.info("Creating new todo with title: {}", request.getTitle());

        // Validate required fields
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            logger.error("Attempted to create todo with empty title");
            throw new IllegalArgumentException("Todo title cannot be empty");
        }

        // Create new todo entity
        Todo todo = new Todo();
        todo.setTitle(request.getTitle().trim());
        todo.setDescription(request.getDescription());
        todo.setCompleted(false); // New todos are always incomplete

        // Save to database
        Todo savedTodo = todoRepository.save(todo);
        logger.info("Successfully created todo with id: {}", savedTodo.getId());

        return convertToDTO(savedTodo);
    }

    /**
     * Updates an existing todo with the provided changes.
     * 
     * <p>
     * This method performs a partial update - only non-null fields
     * in the request will be updated. Null fields are ignored.
     * </p>
     * 
     * @param id      the ID of the todo to update
     * @param request the update request with new values
     * @return the updated todo as a DTO
     * @throws TodoNotFoundException if no todo exists with the given ID
     */
    public TodoDTO updateTodo(Long id, UpdateTodoRequest request) {
        logger.info("Updating todo with id: {}", id);

        // Find existing todo
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Cannot update - todo not found with id: {}", id);
                    return new TodoNotFoundException(id);
                });

        // Apply partial updates (only update non-null fields)
        if (request.getTitle() != null) {
            logger.debug("Updating title for todo {}", id);
            todo.setTitle(request.getTitle().trim());
        }
        if (request.getDescription() != null) {
            logger.debug("Updating description for todo {}", id);
            todo.setDescription(request.getDescription());
        }
        if (request.getCompleted() != null) {
            logger.debug("Updating completion status for todo {} to {}", id, request.getCompleted());
            todo.setCompleted(request.getCompleted());
        }

        // Save changes
        Todo updatedTodo = todoRepository.save(todo);
        logger.info("Successfully updated todo with id: {}", id);

        return convertToDTO(updatedTodo);
    }

    /**
     * Deletes a todo by its ID.
     * 
     * @param id the ID of the todo to delete
     * @throws TodoNotFoundException if no todo exists with the given ID
     */
    public void deleteTodo(Long id) {
        logger.info("Deleting todo with id: {}", id);

        // Verify todo exists before deleting
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Cannot delete - todo not found with id: {}", id);
                    return new TodoNotFoundException(id);
                });

        todoRepository.delete(todo);
        logger.info("Successfully deleted todo with id: {}", id);
    }

    /**
     * Toggles the completion status of a todo.
     * 
     * <p>
     * If the todo is completed, it becomes incomplete, and vice versa.
     * </p>
     * 
     * @param id the ID of the todo to toggle
     * @return the updated todo as a DTO
     * @throws TodoNotFoundException if no todo exists with the given ID
     */
    public TodoDTO toggleTodoCompletion(Long id) {
        logger.info("Toggling completion status for todo with id: {}", id);

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Cannot toggle - todo not found with id: {}", id);
                    return new TodoNotFoundException(id);
                });

        // Toggle the completion status
        boolean newStatus = !todo.getCompleted();
        todo.setCompleted(newStatus);

        Todo updatedTodo = todoRepository.save(todo);
        logger.info("Toggled todo {} completion status to: {}", id, newStatus);

        return convertToDTO(updatedTodo);
    }

    /**
     * Retrieves todos filtered by completion status.
     * 
     * @param completed true for completed todos, false for incomplete todos
     * @return list of todos matching the status, empty list if none found
     */
    public List<TodoDTO> getTodosByStatus(Boolean completed) {
        logger.info("Fetching todos with completion status: {}", completed);

        List<Todo> todos = todoRepository.findByCompleted(completed);
        logger.debug("Found {} todos with completion status: {}", todos.size(), completed);

        return todos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Searches for todos whose title contains the given keyword (case-insensitive).
     * 
     * @param keyword the search keyword
     * @return list of todos matching the search, empty list if none found
     */
    public List<TodoDTO> searchTodos(String keyword) {
        logger.info("Searching todos with keyword: {}", keyword);

        List<Todo> todos = todoRepository.findByTitleContainingIgnoreCase(keyword);
        logger.debug("Found {} todos matching keyword: {}", todos.size(), keyword);

        return todos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converts a Todo entity to a TodoDTO.
     * 
     * <p>
     * This helper method encapsulates the conversion logic and ensures
     * consistent transformation across all service methods.
     * </p>
     * 
     * @param todo the entity to convert
     * @return the DTO representation
     */
    private TodoDTO convertToDTO(Todo todo) {
        return new TodoDTO(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getCompleted(),
                todo.getCreatedAt(),
                todo.getUpdatedAt());
    }

}

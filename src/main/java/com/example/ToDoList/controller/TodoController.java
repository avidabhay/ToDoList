package com.example.ToDoList.controller;

import com.example.ToDoList.dto.CreateTodoRequest;
import com.example.ToDoList.dto.TodoDTO;
import com.example.ToDoList.dto.UpdateTodoRequest;
import com.example.ToDoList.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Todo operations.
 * 
 * <p>
 * This controller exposes HTTP endpoints for managing todos.
 * All endpoints are under the base path /api/todos.
 * </p>
 * 
 * <p>
 * Available endpoints:
 * </p>
 * <ul>
 * <li>GET /api/todos - Get all todos (with optional filters)</li>
 * <li>GET /api/todos/{id} - Get a specific todo</li>
 * <li>POST /api/todos - Create a new todo</li>
 * <li>PUT /api/todos/{id} - Update an existing todo</li>
 * <li>DELETE /api/todos/{id} - Delete a todo</li>
 * <li>PATCH /api/todos/{id}/toggle - Toggle completion status</li>
 * </ul>
 * 
 * <p>
 * This controller follows REST best practices:
 * </p>
 * <ul>
 * <li>Uses appropriate HTTP methods (GET, POST, PUT, DELETE, PATCH)</li>
 * <li>Returns proper HTTP status codes</li>
 * <li>Delegates business logic to the service layer</li>
 * <li>Uses DTOs for request/response bodies</li>
 * </ul>
 * 
 * @author Abhay (avidAbhay)
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);

    @Autowired
    private TodoService todoService;

    /**
     * Retrieves all todos with optional filtering.
     * 
     * <p>
     * This endpoint supports two optional query parameters for filtering:
     * </p>
     * <ul>
     * <li>completed - Filter by completion status (true/false)</li>
     * <li>search - Search by keyword in todo titles</li>
     * </ul>
     * 
     * <p>
     * Examples:
     * </p>
     * 
     * <pre>
     * GET /api/todos                      → All todos
     * GET /api/todos?completed=true       → Only completed todos
     * GET /api/todos?completed=false      → Only incomplete todos
     * GET /api/todos?search=spring        → Todos with "spring" in title
     * </pre>
     * 
     * @param completed optional filter for completion status
     * @param search    optional keyword to search in titles
     * @return ResponseEntity with list of todos and HTTP 200 OK
     */
    @GetMapping
    public ResponseEntity<List<TodoDTO>> getAllTodos(
            @RequestParam(required = false) Boolean completed,
            @RequestParam(required = false) String search) {
        logger.info("GET /api/todos - completed: {}, search: {}", completed, search);

        List<TodoDTO> todos = todoService.getTodos(completed, search);

        logger.debug("Returning {} todos", todos.size());
        return ResponseEntity.ok(todos);
    }

    /**
     * Retrieves a specific todo by its ID.
     * 
     * <p>
     * Example:
     * </p>
     * 
     * <pre>
     * GET / api / todos / 1
     * </pre>
     * 
     * @param id the unique identifier of the todo
     * @return ResponseEntity with the todo and HTTP 200 OK
     * @throws com.example.ToDoList.exception.TodoNotFoundException if todo not
     *                                                              found (returns
     *                                                              HTTP 404)
     */
    @GetMapping("/{id}")
    public ResponseEntity<TodoDTO> getTodoById(@PathVariable Long id) {
        logger.info("GET /api/todos/{}", id);

        TodoDTO todo = todoService.getTodoById(id);

        return ResponseEntity.ok(todo);
    }

    /**
     * Creates a new todo.
     * 
     * <p>
     * The request body should contain the todo title and optional description.
     * The new todo will automatically be set to incomplete.
     * </p>
     * 
     * <p>
     * Example request:
     * </p>
     * 
     * <pre>
     * POST /api/todos
     * Content-Type: application/json
     * 
     * {
     *   "title": "Learn Spring Boot",
     *   "description": "Complete the tutorial"
     * }
     * </pre>
     * 
     * @param request the creation request containing title and description
     * @return ResponseEntity with the created todo and HTTP 201 CREATED
     */
    @PostMapping
    public ResponseEntity<TodoDTO> createTodo(@RequestBody CreateTodoRequest request) {
        logger.info("POST /api/todos - title: {}", request.getTitle());

        TodoDTO createdTodo = todoService.createTodo(request);

        logger.info("Created todo with id: {}", createdTodo.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }

    /**
     * Updates an existing todo.
     * 
     * <p>
     * This endpoint performs a partial update - only the fields provided
     * in the request body will be updated. Null fields are ignored.
     * </p>
     * 
     * <p>
     * Example request:
     * </p>
     * 
     * <pre>
     * PUT /api/todos/1
     * Content-Type: application/json
     * 
     * {
     *   "title": "Updated title",
     *   "completed": true
     * }
     * </pre>
     * 
     * @param id      the ID of the todo to update
     * @param request the update request with new values
     * @return ResponseEntity with the updated todo and HTTP 200 OK
     * @throws com.example.ToDoList.exception.TodoNotFoundException if todo not
     *                                                              found (returns
     *                                                              HTTP 404)
     */
    @PutMapping("/{id}")
    public ResponseEntity<TodoDTO> updateTodo(
            @PathVariable Long id,
            @RequestBody UpdateTodoRequest request) {
        logger.info("PUT /api/todos/{}", id);

        TodoDTO updatedTodo = todoService.updateTodo(id, request);

        logger.info("Updated todo with id: {}", id);
        return ResponseEntity.ok(updatedTodo);
    }

    /**
     * Deletes a todo by its ID.
     * 
     * <p>
     * Example:
     * </p>
     * 
     * <pre>
     * DELETE / api / todos / 1
     * </pre>
     * 
     * @param id the ID of the todo to delete
     * @return ResponseEntity with HTTP 204 NO CONTENT
     * @throws com.example.ToDoList.exception.TodoNotFoundException if todo not
     *                                                              found (returns
     *                                                              HTTP 404)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        logger.info("DELETE /api/todos/{}", id);

        todoService.deleteTodo(id);

        logger.info("Deleted todo with id: {}", id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Toggles the completion status of a todo.
     * 
     * <p>
     * If the todo is completed, it becomes incomplete, and vice versa.
     * This is a convenience endpoint for quickly changing completion status
     * without sending a full update request.
     * </p>
     * 
     * <p>
     * Example:
     * </p>
     * 
     * <pre>
     * PATCH / api / todos / 1 / toggle
     * </pre>
     * 
     * @param id the ID of the todo to toggle
     * @return ResponseEntity with the updated todo and HTTP 200 OK
     * @throws com.example.ToDoList.exception.TodoNotFoundException if todo not
     *                                                              found (returns
     *                                                              HTTP 404)
     */
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<TodoDTO> toggleTodoCompletion(@PathVariable Long id) {
        logger.info("PATCH /api/todos/{}/toggle", id);

        TodoDTO toggledTodo = todoService.toggleTodoCompletion(id);

        logger.info("Toggled completion for todo with id: {}", id);
        return ResponseEntity.ok(toggledTodo);
    }

}

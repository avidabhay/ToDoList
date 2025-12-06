package com.example.ToDoList.controller;

import com.example.ToDoList.dto.CreateTodoRequest;
import com.example.ToDoList.dto.TodoDTO;
import com.example.ToDoList.dto.UpdateTodoRequest;
import com.example.ToDoList.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for Todo operations.
 *
 * @author Abhay (avidAbhay)
 */
@RestController
@RequestMapping("/api/todos")
@Tag(name = "Todo", description = "Todo management APIs")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    @Operation(summary = "Get all todos", description = "Retrieve all todos with optional filters")
    public ResponseEntity<List<TodoDTO>> getAllTodos(
            @RequestParam(required = false) Boolean completed,
            @RequestParam(required = false) String search) {
        List<TodoDTO> todos = todoService.getTodos(completed, search);
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get todo by ID")
    public ResponseEntity<TodoDTO> getTodoById(@PathVariable Long id) {
        TodoDTO todo = todoService.getTodoById(id);
        return ResponseEntity.ok(todo);
    }

    @PostMapping
    @Operation(summary = "Create new todo")
    public ResponseEntity<TodoDTO> createTodo(@RequestBody CreateTodoRequest request) {
        TodoDTO createdTodo = todoService.createTodo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update todo")
    public ResponseEntity<TodoDTO> updateTodo(
            @PathVariable Long id,
            @RequestBody UpdateTodoRequest request) {
        TodoDTO updatedTodo = todoService.updateTodo(id, request);
        return ResponseEntity.ok(updatedTodo);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete todo")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/toggle")
    @Operation(summary = "Toggle todo completion status")
    public ResponseEntity<TodoDTO> toggleTodoCompletion(@PathVariable Long id) {
        TodoDTO toggledTodo = todoService.toggleTodoCompletion(id);
        return ResponseEntity.ok(toggledTodo);
    }
}

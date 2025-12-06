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
 * @author Abhay (avidAbhay)
 */
@Service
@Transactional
public class TodoService {

    private static final Logger logger = LoggerFactory.getLogger(TodoService.class);

    @Autowired
    private TodoRepository todoRepository;

    public List<TodoDTO> getTodos(Boolean completed, String search) {
        if (search != null && !search.isEmpty()) {
            return searchTodos(search);
        }
        if (completed != null) {
            return getTodosByStatus(completed);
        }
        return getAllTodos();
    }

    public List<TodoDTO> getAllTodos() {
        return todoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TodoDTO getTodoById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        return convertToDTO(todo);
    }

    public TodoDTO createTodo(CreateTodoRequest request) {
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Todo title cannot be empty");
        }

        Todo todo = new Todo();
        todo.setTitle(request.getTitle().trim());
        todo.setDescription(request.getDescription());
        todo.setCompleted(false);

        Todo savedTodo = todoRepository.save(todo);
        logger.info("Created todo with id: {}", savedTodo.getId());

        return convertToDTO(savedTodo);
    }

    public TodoDTO updateTodo(Long id, UpdateTodoRequest request) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));

        if (request.getTitle() != null) {
            todo.setTitle(request.getTitle().trim());
        }
        if (request.getDescription() != null) {
            todo.setDescription(request.getDescription());
        }
        if (request.getCompleted() != null) {
            todo.setCompleted(request.getCompleted());
        }

        Todo updatedTodo = todoRepository.save(todo);
        return convertToDTO(updatedTodo);
    }

    public void deleteTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        todoRepository.delete(todo);
    }

    public TodoDTO toggleTodoCompletion(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        todo.setCompleted(!todo.getCompleted());
        Todo updatedTodo = todoRepository.save(todo);
        return convertToDTO(updatedTodo);
    }

    public List<TodoDTO> getTodosByStatus(Boolean completed) {
        return todoRepository.findByCompleted(completed).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TodoDTO> searchTodos(String keyword) {
        return todoRepository.findByTitleContainingIgnoreCase(keyword).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

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

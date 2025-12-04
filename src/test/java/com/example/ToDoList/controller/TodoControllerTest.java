package com.example.ToDoList.controller;

import com.example.ToDoList.dto.CreateTodoRequest;
import com.example.ToDoList.dto.TodoDTO;
import com.example.ToDoList.dto.UpdateTodoRequest;
import com.example.ToDoList.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for TodoController.
 * 
 * <p>
 * These tests use MockMvc to test the HTTP layer without starting
 * a full HTTP server. The service layer is mocked to isolate controller
 * testing.
 * </p>
 * 
 * @author Abhay (avidAbhay)
 * @version 1.0
 * @since 1.0
 */
@WebMvcTest(TodoController.class)
@DisplayName("TodoController Integration Tests")
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TodoService todoService;

    // ==================== GET /api/todos Tests ====================

    @Test
    @DisplayName("GET /api/todos should return all todos")
    void getAllTodos_NoFilters_ReturnsAllTodos() throws Exception {
        // Arrange
        List<TodoDTO> todos = Arrays.asList(
                new TodoDTO(1L, "Todo 1", "Desc 1", false, LocalDateTime.now(), LocalDateTime.now()),
                new TodoDTO(2L, "Todo 2", "Desc 2", true, LocalDateTime.now(), LocalDateTime.now()));
        when(todoService.getTodos(null, null)).thenReturn(todos);

        // Act & Assert
        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Todo 1")))
                .andExpect(jsonPath("$[1].title", is("Todo 2")));

        verify(todoService, times(1)).getTodos(null, null);
    }

    @Test
    @DisplayName("GET /api/todos?completed=true should return completed todos")
    void getAllTodos_CompletedFilter_ReturnsCompletedTodos() throws Exception {
        // Arrange
        List<TodoDTO> completedTodos = Arrays.asList(
                new TodoDTO(2L, "Completed Todo", "Desc", true, LocalDateTime.now(), LocalDateTime.now()));
        when(todoService.getTodos(true, null)).thenReturn(completedTodos);

        // Act & Assert
        mockMvc.perform(get("/api/todos").param("completed", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].completed", is(true)));

        verify(todoService, times(1)).getTodos(true, null);
    }

    @Test
    @DisplayName("GET /api/todos?search=keyword should return search results")
    void getAllTodos_SearchFilter_ReturnsSearchResults() throws Exception {
        // Arrange
        List<TodoDTO> searchResults = Arrays.asList(
                new TodoDTO(1L, "Spring Boot", "Desc", false, LocalDateTime.now(), LocalDateTime.now()));
        when(todoService.getTodos(null, "Spring")).thenReturn(searchResults);

        // Act & Assert
        mockMvc.perform(get("/api/todos").param("search", "Spring"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", containsString("Spring")));

        verify(todoService, times(1)).getTodos(null, "Spring");
    }

    // ==================== GET /api/todos/{id} Tests ====================

    @Test
    @DisplayName("GET /api/todos/{id} should return todo when exists")
    void getTodoById_ValidId_ReturnsTodo() throws Exception {
        // Arrange
        TodoDTO todo = new TodoDTO(1L, "Test Todo", "Desc", false,
                LocalDateTime.now(), LocalDateTime.now());
        when(todoService.getTodoById(1L)).thenReturn(todo);

        // Act & Assert
        mockMvc.perform(get("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test Todo")));

        verify(todoService, times(1)).getTodoById(1L);
    }

    // ==================== POST /api/todos Tests ====================

    @Test
    @DisplayName("POST /api/todos should create new todo")
    void createTodo_ValidRequest_CreatesAndReturns201() throws Exception {
        // Arrange
        CreateTodoRequest request = new CreateTodoRequest("New Todo", "Description");
        TodoDTO createdTodo = new TodoDTO(1L, "New Todo", "Description", false,
                LocalDateTime.now(), LocalDateTime.now());
        when(todoService.createTodo(any(CreateTodoRequest.class))).thenReturn(createdTodo);

        // Act & Assert
        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("New Todo")))
                .andExpect(jsonPath("$.completed", is(false)));

        verify(todoService, times(1)).createTodo(any(CreateTodoRequest.class));
    }

    // ==================== PUT /api/todos/{id} Tests ====================

    @Test
    @DisplayName("PUT /api/todos/{id} should update todo")
    void updateTodo_ValidRequest_UpdatesAndReturns200() throws Exception {
        // Arrange
        UpdateTodoRequest request = new UpdateTodoRequest("Updated Title", "Updated Desc", true);
        TodoDTO updatedTodo = new TodoDTO(1L, "Updated Title", "Updated Desc", true,
                LocalDateTime.now(), LocalDateTime.now());
        when(todoService.updateTodo(eq(1L), any(UpdateTodoRequest.class))).thenReturn(updatedTodo);

        // Act & Assert
        mockMvc.perform(put("/api/todos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Title")))
                .andExpect(jsonPath("$.completed", is(true)));

        verify(todoService, times(1)).updateTodo(eq(1L), any(UpdateTodoRequest.class));
    }

    // ==================== DELETE /api/todos/{id} Tests ====================

    @Test
    @DisplayName("DELETE /api/todos/{id} should delete todo and return 204")
    void deleteTodo_ValidId_DeletesAndReturns204() throws Exception {
        // Arrange
        doNothing().when(todoService).deleteTodo(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/todos/1"))
                .andExpect(status().isNoContent());

        verify(todoService, times(1)).deleteTodo(1L);
    }

    // ==================== PATCH /api/todos/{id}/toggle Tests ====================

    @Test
    @DisplayName("PATCH /api/todos/{id}/toggle should toggle completion")
    void toggleTodoCompletion_ValidId_TogglesAndReturns200() throws Exception {
        // Arrange
        TodoDTO toggledTodo = new TodoDTO(1L, "Test Todo", "Desc", true,
                LocalDateTime.now(), LocalDateTime.now());
        when(todoService.toggleTodoCompletion(1L)).thenReturn(toggledTodo);

        // Act & Assert
        mockMvc.perform(patch("/api/todos/1/toggle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed", is(true)));

        verify(todoService, times(1)).toggleTodoCompletion(1L);
    }

}

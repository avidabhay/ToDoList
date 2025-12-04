package com.example.ToDoList.service;

import com.example.ToDoList.dto.CreateTodoRequest;
import com.example.ToDoList.dto.TodoDTO;
import com.example.ToDoList.dto.UpdateTodoRequest;
import com.example.ToDoList.exception.TodoNotFoundException;
import com.example.ToDoList.model.Todo;
import com.example.ToDoList.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for TodoService.
 * 
 * <p>
 * These tests use Mockito to mock the repository layer and test
 * the service layer in isolation. Each test follows the AAA pattern:
 * Arrange, Act, Assert.
 * </p>
 * 
 * @author Abhay (avidAbhay)
 * @version 1.0
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("TodoService Unit Tests")
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    private Todo sampleTodo;

    @BeforeEach
    void setUp() {
        // Arrange: Create sample test data
        sampleTodo = new Todo();
        sampleTodo.setId(1L);
        sampleTodo.setTitle("Test Todo");
        sampleTodo.setDescription("Test Description");
        sampleTodo.setCompleted(false);
        sampleTodo.setCreatedAt(LocalDateTime.now());
        sampleTodo.setUpdatedAt(LocalDateTime.now());
    }

    // ==================== getTodos() Tests ====================

    @Test
    @DisplayName("Should return all todos when no filters provided")
    void getTodos_NoFilters_ReturnsAllTodos() {
        // Arrange
        List<Todo> todos = Arrays.asList(sampleTodo);
        when(todoRepository.findAll()).thenReturn(todos);

        // Act
        List<TodoDTO> result = todoService.getTodos(null, null);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Todo");
        verify(todoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should filter by completion status when completed parameter provided")
    void getTodos_WithCompletedFilter_ReturnsFilteredTodos() {
        // Arrange
        sampleTodo.setCompleted(true);
        when(todoRepository.findByCompleted(true)).thenReturn(Arrays.asList(sampleTodo));

        // Act
        List<TodoDTO> result = todoService.getTodos(true, null);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCompleted()).isTrue();
        verify(todoRepository, times(1)).findByCompleted(true);
        verify(todoRepository, never()).findAll();
    }

    @Test
    @DisplayName("Should search by keyword when search parameter provided")
    void getTodos_WithSearchKeyword_ReturnsSearchResults() {
        // Arrange
        when(todoRepository.findByTitleContainingIgnoreCase("Test"))
                .thenReturn(Arrays.asList(sampleTodo));

        // Act
        List<TodoDTO> result = todoService.getTodos(null, "Test");

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).contains("Test");
        verify(todoRepository, times(1)).findByTitleContainingIgnoreCase("Test");
        verify(todoRepository, never()).findAll();
    }

    @Test
    @DisplayName("Should prioritize search over completion filter")
    void getTodos_WithBothFilters_PrioritizesSearch() {
        // Arrange
        when(todoRepository.findByTitleContainingIgnoreCase("Test"))
                .thenReturn(Arrays.asList(sampleTodo));

        // Act
        todoService.getTodos(true, "Test");

        // Assert
        verify(todoRepository, times(1)).findByTitleContainingIgnoreCase("Test");
        verify(todoRepository, never()).findByCompleted(any());
    }

    // ==================== getTodoById() Tests ====================

    @Test
    @DisplayName("Should return todo when valid ID provided")
    void getTodoById_ValidId_ReturnsTodo() {
        // Arrange
        when(todoRepository.findById(1L)).thenReturn(Optional.of(sampleTodo));

        // Act
        TodoDTO result = todoService.getTodoById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Test Todo");
        verify(todoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw TodoNotFoundException when invalid ID provided")
    void getTodoById_InvalidId_ThrowsException() {
        // Arrange
        when(todoRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> todoService.getTodoById(999L))
                .isInstanceOf(TodoNotFoundException.class)
                .hasMessageContaining("999");

        verify(todoRepository, times(1)).findById(999L);
    }

    // ==================== createTodo() Tests ====================

    @Test
    @DisplayName("Should create todo with valid request")
    void createTodo_ValidRequest_CreatesTodo() {
        // Arrange
        CreateTodoRequest request = new CreateTodoRequest("New Todo", "Description");
        when(todoRepository.save(any(Todo.class))).thenReturn(sampleTodo);

        // Act
        TodoDTO result = todoService.createTodo(request);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Test Todo");
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    @DisplayName("Should throw exception when title is null")
    void createTodo_NullTitle_ThrowsException() {
        // Arrange
        CreateTodoRequest request = new CreateTodoRequest(null, "Description");

        // Act & Assert
        assertThatThrownBy(() -> todoService.createTodo(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("title cannot be empty");

        verify(todoRepository, never()).save(any(Todo.class));
    }

    @Test
    @DisplayName("Should throw exception when title is empty")
    void createTodo_EmptyTitle_ThrowsException() {
        // Arrange
        CreateTodoRequest request = new CreateTodoRequest("   ", "Description");

        // Act & Assert
        assertThatThrownBy(() -> todoService.createTodo(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("title cannot be empty");

        verify(todoRepository, never()).save(any(Todo.class));
    }

    @Test
    @DisplayName("Should trim title whitespace when creating todo")
    void createTodo_TitleWithWhitespace_TrimsTitle() {
        // Arrange
        CreateTodoRequest request = new CreateTodoRequest("  Spaced Title  ", "Description");
        Todo savedTodo = new Todo();
        savedTodo.setTitle("Spaced Title");
        when(todoRepository.save(any(Todo.class))).thenReturn(savedTodo);

        // Act
        todoService.createTodo(request);

        // Assert
        verify(todoRepository).save(argThat(todo -> todo.getTitle().equals("Spaced Title")));
    }

    // ==================== updateTodo() Tests ====================

    @Test
    @DisplayName("Should update todo with valid request")
    void updateTodo_ValidRequest_UpdatesTodo() {
        // Arrange
        UpdateTodoRequest request = new UpdateTodoRequest("Updated Title", "Updated Desc", true);
        when(todoRepository.findById(1L)).thenReturn(Optional.of(sampleTodo));
        when(todoRepository.save(any(Todo.class))).thenReturn(sampleTodo);

        // Act
        TodoDTO result = todoService.updateTodo(1L, request);

        // Assert
        assertThat(result).isNotNull();
        verify(todoRepository, times(1)).findById(1L);
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    @DisplayName("Should perform partial update when only some fields provided")
    void updateTodo_PartialUpdate_UpdatesOnlyProvidedFields() {
        // Arrange
        UpdateTodoRequest request = new UpdateTodoRequest("Updated Title", null, null);
        when(todoRepository.findById(1L)).thenReturn(Optional.of(sampleTodo));
        when(todoRepository.save(any(Todo.class))).thenReturn(sampleTodo);

        // Act
        todoService.updateTodo(1L, request);

        // Assert
        verify(todoRepository).save(argThat(todo -> todo.getTitle().equals("Updated Title") &&
                todo.getDescription().equals("Test Description") // Unchanged
        ));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent todo")
    void updateTodo_InvalidId_ThrowsException() {
        // Arrange
        UpdateTodoRequest request = new UpdateTodoRequest("Title", "Desc", true);
        when(todoRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> todoService.updateTodo(999L, request))
                .isInstanceOf(TodoNotFoundException.class);

        verify(todoRepository, never()).save(any(Todo.class));
    }

    // ==================== deleteTodo() Tests ====================

    @Test
    @DisplayName("Should delete todo when valid ID provided")
    void deleteTodo_ValidId_DeletesTodo() {
        // Arrange
        when(todoRepository.findById(1L)).thenReturn(Optional.of(sampleTodo));
        doNothing().when(todoRepository).delete(any(Todo.class));

        // Act
        todoService.deleteTodo(1L);

        // Assert
        verify(todoRepository, times(1)).findById(1L);
        verify(todoRepository, times(1)).delete(sampleTodo);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent todo")
    void deleteTodo_InvalidId_ThrowsException() {
        // Arrange
        when(todoRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> todoService.deleteTodo(999L))
                .isInstanceOf(TodoNotFoundException.class);

        verify(todoRepository, never()).delete(any(Todo.class));
    }

    // ==================== toggleTodoCompletion() Tests ====================

    @Test
    @DisplayName("Should toggle completion from false to true")
    void toggleTodoCompletion_FromFalseToTrue_TogglesStatus() {
        // Arrange
        sampleTodo.setCompleted(false);
        when(todoRepository.findById(1L)).thenReturn(Optional.of(sampleTodo));
        when(todoRepository.save(any(Todo.class))).thenReturn(sampleTodo);

        // Act
        todoService.toggleTodoCompletion(1L);

        // Assert
        verify(todoRepository).save(argThat(todo -> todo.getCompleted() == true));
    }

    @Test
    @DisplayName("Should toggle completion from true to false")
    void toggleTodoCompletion_FromTrueToFalse_TogglesStatus() {
        // Arrange
        sampleTodo.setCompleted(true);
        when(todoRepository.findById(1L)).thenReturn(Optional.of(sampleTodo));
        when(todoRepository.save(any(Todo.class))).thenReturn(sampleTodo);

        // Act
        todoService.toggleTodoCompletion(1L);

        // Assert
        verify(todoRepository).save(argThat(todo -> todo.getCompleted() == false));
    }

    @Test
    @DisplayName("Should throw exception when toggling non-existent todo")
    void toggleTodoCompletion_InvalidId_ThrowsException() {
        // Arrange
        when(todoRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> todoService.toggleTodoCompletion(999L))
                .isInstanceOf(TodoNotFoundException.class);

        verify(todoRepository, never()).save(any(Todo.class));
    }

    // ==================== searchTodos() Tests ====================

    @Test
    @DisplayName("Should return todos matching search keyword")
    void searchTodos_ValidKeyword_ReturnsMatchingTodos() {
        // Arrange
        when(todoRepository.findByTitleContainingIgnoreCase("Test"))
                .thenReturn(Arrays.asList(sampleTodo));

        // Act
        List<TodoDTO> result = todoService.searchTodos("Test");

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).contains("Test");
    }

    @Test
    @DisplayName("Should return empty list when no todos match search")
    void searchTodos_NoMatches_ReturnsEmptyList() {
        // Arrange
        when(todoRepository.findByTitleContainingIgnoreCase("NonExistent"))
                .thenReturn(Arrays.asList());

        // Act
        List<TodoDTO> result = todoService.searchTodos("NonExistent");

        // Assert
        assertThat(result).isEmpty();
    }

}

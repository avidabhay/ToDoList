package com.example.ToDoList.repository;

import com.example.ToDoList.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Todo entity database operations.
 * 
 * <p>
 * This interface extends JpaRepository to provide CRUD operations
 * and custom query methods for the Todo entity. Spring Data JPA
 * automatically implements this interface at runtime.
 * </p>
 * 
 * <p>
 * Custom query methods follow Spring Data JPA naming conventions
 * and are automatically implemented based on method names.
 * </p>
 * 
 * @author Abhay (avidAbhay)
 * @version 1.0
 * @since 1.0
 * @see Todo
 * @see JpaRepository
 */
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    /**
     * Finds all todos with the specified completion status.
     * 
     * <p>
     * This method uses Spring Data JPA query derivation to automatically
     * generate the SQL query: SELECT * FROM todos WHERE completed = ?
     * </p>
     * 
     * <p>
     * Example usage:
     * </p>
     * 
     * <pre>
     * // Get all completed todos
     * List&lt;Todo&gt; completedTodos = todoRepository.findByCompleted(true);
     * 
     * // Get all incomplete todos
     * List&lt;Todo&gt; incompleteTodos = todoRepository.findByCompleted(false);
     * </pre>
     * 
     * @param completed the completion status to filter by (true for completed,
     *                  false for incomplete)
     * @return a list of todos matching the completion status, empty list if none
     *         found
     */
    List<Todo> findByCompleted(Boolean completed);

    /**
     * Searches for todos whose title contains the specified keyword
     * (case-insensitive).
     * 
     * <p>
     * This method performs a case-insensitive partial match on the title field.
     * The generated SQL uses LIKE with wildcards: WHERE LOWER(title) LIKE
     * LOWER('%keyword%')
     * </p>
     * 
     * <p>
     * Example usage:
     * </p>
     * 
     * <pre>
     * // Find all todos with "spring" in the title (matches "Spring Boot",
     * // "spring mvc", etc.)
     * List&lt;Todo&gt; springTodos = todoRepository.findByTitleContainingIgnoreCase("spring");
     * </pre>
     * 
     * @param keyword the search keyword to look for in todo titles
     * @return a list of todos whose titles contain the keyword, empty list if none
     *         found
     */
    List<Todo> findByTitleContainingIgnoreCase(String keyword);

}

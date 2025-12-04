package com.example.ToDoList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the ToDoList REST API.
 * 
 * <p>
 * This is the entry point of the Spring Boot application.
 * The @SpringBootApplication annotation enables:
 * </p>
 * <ul>
 * <li>@Configuration - Marks this as a configuration class</li>
 * <li>@EnableAutoConfiguration - Enables Spring Boot's auto-configuration</li>
 * <li>@ComponentScan - Scans for components in this package and
 * sub-packages</li>
 * </ul>
 * 
 * <p>
 * To run the application:
 * </p>
 * 
 * <pre>
 * mvn spring-boot:run
 * </pre>
 * 
 * <p>
 * The application will start on port 8080 by default.
 * Access the API at: http://localhost:8080/api/todos
 * </p>
 * 
 * @author Abhay (avidAbhay)
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
public class ToDoListApplication {

    /**
     * Main method to start the Spring Boot application.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        SpringApplication.run(ToDoListApplication.class, args);
    }

}

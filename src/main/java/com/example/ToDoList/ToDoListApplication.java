package com.example.ToDoList;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class.
 *
 * @author Abhay (avidAbhay)
 */
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "ToDoList API", version = "1.0", description = "REST API for managing todos"))
public class ToDoListApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToDoListApplication.class, args);
    }
}

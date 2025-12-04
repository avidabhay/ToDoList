# ToDoList REST API

A production-ready Spring Boot REST API for managing todos with full CRUD operations, built with clean architecture and comprehensive test coverage.

## 🚀 Features

- ✅ Full CRUD operations for todos
- ✅ Filter by completion status
- ✅ Search by keyword
- ✅ Toggle completion status
- ✅ RESTful API design
- ✅ Comprehensive test coverage (28 tests)
- ✅ Production-ready with PostgreSQL support
- ✅ H2 database for local development

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot 3.2.1**
- **Spring Data JPA**
- **PostgreSQL** (Production)
- **H2 Database** (Development)
- **JUnit 5 & Mockito** (Testing)
- **Maven**

## 📋 Prerequisites

- Java 21 or higher
- Maven 3.6+

## 🏃 Running Locally

```bash
# Clone the repository
git clone <your-repo-url>
cd ToDoList

# Run the application
mvn spring-boot:run

# The API will be available at http://localhost:8080
```

## 🧪 Running Tests

```bash
# Run all tests
mvn test

# Run with coverage
mvn test jacoco:report
```

## 📚 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/todos` | Get all todos |
| GET | `/api/todos?completed=true` | Get completed todos |
| GET | `/api/todos?search=keyword` | Search todos |
| GET | `/api/todos/{id}` | Get todo by ID |
| POST | `/api/todos` | Create new todo |
| PUT | `/api/todos/{id}` | Update todo |
| DELETE | `/api/todos/{id}` | Delete todo |
| PATCH | `/api/todos/{id}/toggle` | Toggle completion |

## 📝 Example Requests

### Create a Todo
```bash
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -d '{"title":"Learn Spring Boot","description":"Complete tutorial"}'
```

### Get All Todos
```bash
curl http://localhost:8080/api/todos
```

### Toggle Completion
```bash
curl -X PATCH http://localhost:8080/api/todos/1/toggle
```

## 🗄️ Database

- **Local Development:** H2 in-memory database
- **Production:** PostgreSQL

Access H2 Console (local only): http://localhost:8080/h2-console

## 🚀 Deployment

This application is configured for deployment on Render.com with PostgreSQL.

## 👤 Author

**Abhay** (avidAbhay)

## 📄 License

This project is open source and available under the MIT License.

# Setup Guide

## Development Setup

### Prerequisites
- Java 21
- Maven 3.6+

### Steps

1. **Clone Repository**
   ```bash
   git clone https://github.com/avidAbhay/todolist-api.git
   cd ToDoList
   ```

2. **Install Dependencies**
   ```bash
   mvn clean install
   ```

3. **Run Application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access API**
   - API: http://localhost:8080/api/todos
   - H2 Console: http://localhost:8080/h2-console
   - API Docs: http://localhost:8080/swagger-ui.html

### Configuration

The application uses H2 in-memory database by default. Configuration is in `src/main/resources/application-dev.properties`.

---

## Production Setup

### Prerequisites
- Docker
- Docker Compose

### Steps

1. **Navigate to Docker Folder**
   ```bash
   cd docker
   ```

2. **Start Services**
   ```bash
   docker compose up --build
   ```

3. **Access API**
   - API: http://localhost:8080/api/todos
   - API Docs: http://localhost:8080/swagger-ui.html

### Configuration

Production uses PostgreSQL database. Configuration is in `src/main/resources/application-prod.properties`.

Database credentials (default):
- Host: postgres
- Port: 5432
- Database: todolist
- Username: todolist
- Password: todolist123

---

## Troubleshooting

See [FAQ section in README](../README.md#faq) for common issues and solutions.

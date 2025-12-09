# ToDoList REST API

A production-ready Spring Boot REST API for managing todos.

---

## Prerequisites

- **Java 21** or higher
- **Maven 3.6+**
- **Docker & Docker Compose** (for production deployment)

---

## Installation

### Development Environment

```bash
# Clone repository
git clone https://github.com/avidabhay/ToDoList.git
cd ToDoList

# Install dependencies
mvn clean install

# Run application
mvn spring-boot:run
```

Access API: http://localhost:8080/api/todos

### Production Environment

```bash
# Navigate to docker folder
cd docker

# Start application with PostgreSQL
docker compose up --build
```

Access API: http://localhost:8080/api/todos

---

## Quick Start

### Local Development (H2 Database)

```bash
mvn spring-boot:run
```

**Database:** H2 in-memory (data resets on restart)

### Production (Docker + PostgreSQL)

```bash
cd docker
docker compose up
```

**Database:** PostgreSQL (persistent data)

---

## Project Structure

```
ToDoList/
├── docker/                  # Docker configuration
├── api-docs/                # API documentation
├── docs/                    # Project documentation
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
└── pom.xml
```

---

## Configuration

### Profiles

- **dev** - H2 database (default for local development)
- **prod** - PostgreSQL database (for production)

### Environment Variables

For production deployment:

```bash
SPRING_PROFILES_ACTIVE=prod
DATABASE_URL=jdbc:postgresql://host:5432/todolist
```

---

## FAQ

### Port already in use?

```bash
# Find process using port 8080
lsof -i :8080

# Kill the process
kill -9 <PID>
```

### Docker build fails?

```bash
# Clean Docker cache
docker system prune -a

# Rebuild from scratch
cd docker
docker compose up --build --force-recreate
```

### Database connection error?

**Development:** Ensure H2 is configured in `application-dev.properties`

**Production:** Check PostgreSQL container status:
```bash
docker compose ps
docker compose logs postgres
```

### Application won't start?

```bash
# Check logs
docker compose logs app

# Verify Java version
java -version  # Should be Java 21

# Clean and rebuild
mvn clean install
```

---

## Documentation

- **API Documentation:** See [api-docs/](api-docs/) folder
- **Setup Guides:** See [docs/](docs/) folder

---

**Made with ❤️ using Spring Boot**

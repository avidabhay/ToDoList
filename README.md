# ToDoList REST API

A production-ready Spring Boot REST API for managing todos with full CRUD operations, comprehensive test coverage, and multiple deployment options.

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Tests](https://img.shields.io/badge/Tests-28%20passing-success.svg)](#-running-tests)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue.svg)](#-docker-deployment)

---

## � Table of Contents

- [Features](#-features)
- [Tech Stack](#️-tech-stack)
- [Quick Start](#-quick-start)
- [Deployment Options](#-deployment-options)
- [Command Reference](#-command-reference)
- [API Endpoints](#-api-endpoints)
- [Database Configuration](#️-database-configuration)
- [Testing](#-testing)
- [Project Structure](#-project-structure)
- [Documentation](#-documentation)

---

## 🚀 Features

- ✅ Full CRUD operations (Create, Read, Update, Delete)
- ✅ Search todos by keyword
- ✅ Filter by completion status
- ✅ Toggle completion with single endpoint
- ✅ RESTful API design
- ✅ 28 comprehensive tests (20 unit + 8 integration)
- ✅ Multiple database support (H2, MySQL, PostgreSQL)
- ✅ Docker & Docker Compose ready
- ✅ Production deployment ready (Render.com)

---

## 🛠️ Tech Stack

| Category | Technology |
|----------|------------|
| **Language** | Java 21 |
| **Framework** | Spring Boot 3.2.1 |
| **Data Access** | Spring Data JPA |
| **Databases** | H2 (dev), MySQL 8.0, PostgreSQL 16 |
| **Testing** | JUnit 5, Mockito, AssertJ, MockMvc |
| **Build Tool** | Maven |
| **Containerization** | Docker, Docker Compose |

---

## ⚡ Quick Start

```bash
# Clone the repository
git clone https://github.com/avidAbhay/todolist-api.git
cd ToDoList

# Run with Maven (H2 database)
mvn spring-boot:run

# Access API
curl http://localhost:8080/api/todos
```

**That's it!** The API is now running on http://localhost:8080

---

## 🎯 Deployment Options

Choose the option that best fits your needs:

| Option | Database | Persistent | Docker | Best For |
|--------|----------|------------|--------|----------|
| **1. Maven + H2** | H2 | ❌ | ❌ | Quick development |
| **2. Docker + H2** | H2 | ❌ | ✅ | Docker testing |
| **3. Docker + MySQL** | MySQL | ✅ | ✅ | Local with persistence |
| **4. Docker + PostgreSQL** | PostgreSQL | ✅ | ✅ | Production-like |
| **5. Render.com** | PostgreSQL | ✅ | ✅ | Production |

---

## 📖 Command Reference

### Option 1: Maven + H2

```bash
# Start
mvn spring-boot:run

# Stop
Ctrl+C

# Access
# API: http://localhost:8080/api/todos
# H2 Console: http://localhost:8080/h2-console
```

---

### Option 2: Docker + H2

```bash
# Start
docker compose -f docker-compose-local.yml up --build

# Start (detached)
docker compose -f docker-compose-local.yml up -d

# View logs
docker compose -f docker-compose-local.yml logs -f app

# Stop
docker compose -f docker-compose-local.yml down

# Debug: Check container status
docker compose -f docker-compose-local.yml ps
```

---

### Option 3: Docker + MySQL

```bash
# Start
docker compose -f docker-compose-mysql.yml up --build

# Start (detached)
docker compose -f docker-compose-mysql.yml up -d

# View logs
docker compose -f docker-compose-mysql.yml logs -f app
docker compose -f docker-compose-mysql.yml logs -f mysql

# Stop (keep data)
docker compose -f docker-compose-mysql.yml down

# Stop (remove data)
docker compose -f docker-compose-mysql.yml down -v

# Debug: Connect to MySQL
docker exec -it todolist-mysql mysql -u todolist -ptodolist123 todolist
```

---

### Option 4: Docker + PostgreSQL

```bash
# Start
docker compose up --build

# Start (detached)
docker compose up -d

# View logs
docker compose logs -f app
docker compose logs -f postgres

# Stop (keep data)
docker compose down

# Stop (remove data)
docker compose down -v

# Debug: Connect to PostgreSQL
docker exec -it todolist-db psql -U todolist -d todolist
```

---

### Option 5: Render.com (Production)

See [Deployment Guide](docs/project-documentation/deployment_guide.md) for complete instructions.

---

## 🔧 Common Commands

### Rebuild After Code Changes
```bash
# Maven
mvn clean install

# Docker (any option)
docker compose -f <compose-file> up --build --force-recreate
```

### Clean Up Docker
```bash
# Stop all containers
docker compose down -v

# Remove all unused Docker resources
docker system prune -a
```

### Check What's Running
```bash
# Check port 8080
lsof -i :8080

# Check Docker containers
docker ps

# Check all processes
docker compose ps
```

---

## 📚 API Endpoints

### Base URL
```
http://localhost:8080/api/todos
```

### Endpoints

| Method | Endpoint | Description | Example |
|--------|----------|-------------|---------|
| **GET** | `/api/todos` | Get all todos | `curl http://localhost:8080/api/todos` |
| **GET** | `/api/todos?completed=true` | Get completed todos | `curl http://localhost:8080/api/todos?completed=true` |
| **GET** | `/api/todos?search=keyword` | Search todos | `curl http://localhost:8080/api/todos?search=Spring` |
| **GET** | `/api/todos/{id}` | Get todo by ID | `curl http://localhost:8080/api/todos/1` |
| **POST** | `/api/todos` | Create new todo | See below |
| **PUT** | `/api/todos/{id}` | Update todo | See below |
| **PATCH** | `/api/todos/{id}/toggle` | Toggle completion | `curl -X PATCH http://localhost:8080/api/todos/1/toggle` |
| **DELETE** | `/api/todos/{id}` | Delete todo | `curl -X DELETE http://localhost:8080/api/todos/1` |

### Example Requests

**Create Todo:**
```bash
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -d '{"title":"Learn Spring Boot","description":"Complete tutorial"}'
```

**Update Todo:**
```bash
curl -X PUT http://localhost:8080/api/todos/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"Updated Title","completed":true}'
```

---

## 🗄️ Database Configuration

### H2 (Development)
```properties
Profile: dev
URL: jdbc:h2:mem:tododb
Console: http://localhost:8080/h2-console
Username: sa
Password: (empty)
```

### MySQL (Docker)
```properties
Profile: mysql
Host: localhost
Port: 3306
Database: todolist
Username: todolist
Password: todolist123
```

### PostgreSQL (Docker/Production)
```properties
Profile: prod
Host: localhost
Port: 5432
Database: todolist
Username: todolist
Password: todolist123
```

---

## 🧪 Testing

### Run All Tests
```bash
mvn test
```

**Output:**
```
Tests run: 28, Failures: 0, Errors: 0, Skipped: 0
```

### Run Specific Test Class
```bash
mvn test -Dtest=TodoServiceTest
mvn test -Dtest=TodoControllerTest
```

### Test Coverage
- **20 Unit Tests** (TodoService)
- **8 Integration Tests** (TodoController)
- **100% Coverage** of service and controller methods

---

## 📁 Project Structure

```
ToDoList/
├── src/
│   ├── main/
│   │   ├── java/com/example/ToDoList/
│   │   │   ├── controller/          # REST endpoints
│   │   │   ├── service/             # Business logic
│   │   │   ├── repository/          # Data access
│   │   │   ├── model/               # JPA entities
│   │   │   ├── dto/                 # Request/Response objects
│   │   │   └── exception/           # Error handling
│   │   └── resources/
│   │       ├── application.properties           # Default config
│   │       ├── application-dev.properties       # H2 config
│   │       ├── application-mysql.properties     # MySQL config
│   │       └── application-prod.properties      # PostgreSQL config
│   └── test/                        # Unit & integration tests
├── docs/                            # Documentation (gitignored)
├── Dockerfile                       # Docker image definition
├── docker-compose.yml               # PostgreSQL setup
├── docker-compose-mysql.yml         # MySQL setup
├── docker-compose-local.yml         # H2 setup
├── pom.xml                          # Maven dependencies
└── README.md                        # This file
```

---

##  Documentation

> **📁 Note:** The `/docs` folder is gitignored and not included in the repository.  
> These documentation files are for local development reference only.

- [Configuration Guide](docs/project-documentation/configuration_guide.md) 📄 *Local only*
- [Deployment Options Summary](docs/project-documentation/deployment_options_summary.md) 📄 *Local only*
- [Deployment Guide](docs/project-documentation/deployment_guide.md) 📄 *Local only*
- [Testing Checklist](docs/project-documentation/testing_checklist.md) 📄 *Local only*
- [Code Quality Improvements](docs/project-documentation/code_quality_improvements.md) 📄 *Local only*
- [Test Coverage Report](docs/project-documentation/test_coverage_report.md) 📄 *Local only*
- [Future Scope](docs/project-documentation/future_scope.md) 📄 *Local only*
- [Docker Setup Guide](docs/project-documentation/docker_setup_guide.md) 📄 *Local only*
- [POM.xml Study Guide](docs/pom_xml_study_guide.md) 📄 *Local only*
- [Spring Boot Folder Structure](docs/spring_boot_folder_structure.md) 📄 *Local only*

---

## � Troubleshooting

### Port Already in Use
```bash
# Find process using port 8080
lsof -i :8080

# Kill the process
kill -9 <PID>
```

### Docker Issues
```bash
# Clean up everything
docker compose down -v
docker system prune -a

# Restart Docker
sudo systemctl restart docker
```

### Maven Build Fails
```bash
# Clean and rebuild
mvn clean install

# Skip tests
mvn clean install -DskipTests
```

---

## 👤 Author

**Abhay** (avidAbhay)

---

## 📄 License

This project is open source and available under the MIT License.

---

## 🎯 Quick Links

- [API Documentation](#-api-endpoints)
- [Deployment Options](#-deployment-options)
- [Command Reference](#-command-reference)
- [Testing Guide](#-testing)
- [Troubleshooting](#-troubleshooting)

---

**Made with ❤️ using Spring Boot**

# API Documentation

API documentation is automatically generated using Swagger/OpenAPI.

## Access

When the application is running, access the interactive API documentation at:

**Swagger UI:** http://localhost:8080/swagger-ui.html

**OpenAPI Spec:** http://localhost:8080/v3/api-docs

## Endpoints

All endpoints are under `/api/todos`:

- `GET /api/todos` - Get all todos (with optional filters)
- `GET /api/todos/{id}` - Get todo by ID
- `POST /api/todos` - Create new todo
- `PUT /api/todos/{id}` - Update todo
- `DELETE /api/todos/{id}` - Delete todo
- `PATCH /api/todos/{id}/toggle` - Toggle completion status

## Query Parameters

- `completed` (boolean) - Filter by completion status
- `search` (string) - Search todos by title

## Example Requests

See Swagger UI for interactive examples and request/response schemas.

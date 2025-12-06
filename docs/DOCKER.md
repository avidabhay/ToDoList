# Docker Deployment Guide

## Quick Start

```bash
cd docker
docker compose up --build
```

---

## Configuration

### Environment Variables

The application uses these environment variables in production:

- `SPRING_PROFILES_ACTIVE=prod` - Activates production profile
- `DATABASE_URL` - PostgreSQL connection string

### Database Configuration

Default PostgreSQL settings (configured in docker-compose.yml):

```yaml
Host: postgres
Port: 5432
Database: todolist
Username: todolist
Password: todolist123
```

---

## Commands

### Start Services
```bash
docker compose up
```

### Start in Background
```bash
docker compose up -d
```

### View Logs
```bash
docker compose logs -f app
docker compose logs -f postgres
```

### Stop Services
```bash
docker compose down
```

### Stop and Remove Data
```bash
docker compose down -v
```

### Rebuild
```bash
docker compose up --build --force-recreate
```

---

## Troubleshooting

### Build Fails

```bash
# Clean Docker cache
docker system prune -a

# Rebuild
docker compose up --build
```

### Container Won't Start

```bash
# Check container status
docker compose ps

# View logs
docker compose logs app
```

### Database Connection Error

```bash
# Check PostgreSQL container
docker compose logs postgres

# Verify database is healthy
docker compose ps
```

---

## File Structure

```
docker/
├── Dockerfile           # Multi-stage build configuration
├── docker-compose.yml   # Service orchestration
└── .dockerignore        # Build exclusions
```

# Incident Management System (IMS) — Spring Boot REST API

A RESTful API for managing IT incidents, built with Spring Boot and PostgreSQL.

## About This Project

This is the **Spring Boot version** of my Incident Management System, part of a deliberate learning path.

A previous **CLI version** was built using raw JDBC to master database persistence fundamentals 
(connection management, prepared statements, manual ResultSet mapping) before adopting Spring Boot abstractions. 
This approach demonstrates understanding of what frameworks abstract away, not just how to use them.

## Tech Stack

- **Java 21**
- **Spring Boot 4.x**
- **Spring Data JPA / Hibernate**
- **PostgreSQL** (via Docker)
- **Maven**

## Prerequisites

- Java 21
- Docker Desktop
- Maven

## How to Run

### 1. Start PostgreSQL with Docker
```bash
docker run --name postgres-ims \
  -e POSTGRES_PASSWORD=admin \
  -e POSTGRES_DB=ims_db \
  -p 5432:5432 \
  -d postgres:16
```

### 2. Run the application
```bash
mvn spring-boot:run
```

### 3. The API will be available at
```
http://localhost:8080/api/incidents
```

## API Endpoints

### `POST /api/incidents`
Creates a new incident. Returns `201 Created`.

### `GET /api/incidents`
Returns a list of all incidents. Returns `200 OK`.

### `GET /api/incidents/{id}`
Returns a single incident by ID. Returns `200 OK` or `404 Not Found`.

### `PUT /api/incidents/{id}`
Updates an existing incident. Returns `200 OK` or `404 Not Found`.

### `DELETE /api/incidents/{id}`
Deletes an incident by ID. Returns `204 No Content` or `404 Not Found`.

---

### Request Body Example (POST/PUT)
```json
{
    "title": "Server down",
    "description": "Database not responding",
    "priority": "CRITICAL",
    "source": "USER_REPORT",
    "assignedTeam": "L1",
    "assignedTo": "vromita"
}
```
## Error Response Example

All errors return a standardized response format:

```json
{
    "status": 404,
    "message": "Incident with id 999 not found",
    "timestamp": "2026-05-14T22:00:00"
}
```

Validation errors include field-level details:

```json
{
    "status": 400,
    "message": "Validation Failed",
    "timestamp": "2026-05-14T22:00:00",
    "errors": {
        "title": "Title is required",
        "priority": "Priority is required"
    }
}
```

### Priority Levels & SLA
- `CRITICAL` → 4 hours
- `HIGH` → 12 hours
- `MEDIUM` → 24 hours
- `LOW` → 48 hours

## Project Structure

```
src/main/java/com/vromita/incident_management_system/
├── controller/        # REST Controllers (HTTP layer)
├── service/           # Business logic (SLA calculation, validation)
├── repository/        # Data access layer (Spring Data JPA)
├── model/             # JPA Entities and Enums
├── dto/               # Data Transfer Objects
├── mapper/            # DTO to Entity converters
└── exception/         # Custom exceptions and global handler
```

## Testing

```bash
mvn test
```

6 unit tests covering the Service layer with JUnit 5 and Mockito.

## Versions

### v1.0.0 — REST API Core
- Full CRUD REST API
- Automatic SLA calculation by priority
- SLA recalculation when priority changes
- Automatic `closedAt` timestamp lifecycle
- Bean Validation with `@Valid`, `@NotBlank`, `@NotNull`
- Centralized error handling with `@ControllerAdvice`
- Standardized `ErrorResponse` DTO with Jackson `@JsonInclude`
- JUnit 5 + Mockito test coverage on Service layer

## Roadmap

- **v2.0** — Spring Security + JWT Authentication
- **v2.1** — RBAC with L1/L2/L3 roles
- **v2.2** — Comment System (SLA reset on team comment)
- **v3.0** — SLA Reporting + Breach detection + Automatic escalation

## 👨‍💻 Author

**Valerio Romita**  
Building Java expertise from fundamentals to enterprise frameworks.

[GitHub](https://github.com/VROMITA) | [LinkedIn](https://www.linkedin.com/in/valerio-romita)
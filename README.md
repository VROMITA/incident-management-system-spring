![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.x-green)
![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![Maven](https://img.shields.io/badge/Maven-3.x-red)

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
- **Spring Security + JJWT**
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

### 2. Create application-local.yaml
Create `src/main/resources/application-local.yaml`:

```yaml
spring:
  datasource:
    password: <same password used in Docker command above>

jwt:
  secret: "generate with: openssl rand -base64 32"
```

### 3. Run the application
```bash
mvn spring-boot:run
```

### 4. Access Swagger UI
```
http://localhost:8080/swagger-ui.html
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
Deletes an incident by ID. Returns `204 No Content` or `404 Not Found`. - Only L3 role

### `POST /auth/login`
Login with username and password - authentication required

### `POST /auth/logout`
Logout with `200 OK`, plus logout message

### `POST /api/incidents/{id}/comments`
Create a new comment on the selected incident

### `GET /api/incidents/{id}/comments`
Retrieve all the comments on the selected incident

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

Not authorized error for deleting an incident

```json
{
"message": "Access Denied",
"status": 403,
"timestamp": "2026-05-23T19:27:41.252896"
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
├── exception/         # Custom exceptions and global handler
└── security/          # JWT filter, utility and security configuration

```

## Testing

```bash
mvn test
```

12 unit tests covering the Service layer with JUnit 5 and Mockito.

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

### v2.0 — Spring Security + JWT Authentication
- JWT token-based authentication (stateless)
- Login endpoint POST /auth/login
- BCrypt password encoding
- Role-based access control (RBAC) — L1, L2, L3
- Delete restricted to L3 via @PreAuthorize
- 403 Forbidden for unauthorized actions

### v2.1 — Comment System

- Comment System: User can add comments and retrieve comments from incidents
- Automatic SLA reset when the assigned team responds with a comment

### v2.1.1 — Logout Endpoint

- Logout endpoint with 200 status code + Logout Message - POST /auth/logout
- Being stateless, the server does not keep in memory the token. The client should delete it.
  Therefore, from server perspective the token will stay active until the expiration.

### v2.3 — RBAC + Escalation
- RBAC check on updateIncident — only the assigned team can modify a ticket
- Escalation validation — L1 can only escalate to L2
- SLA reset on team or priority change
- assignedTo migrated from String to AppUser (foreign key)
- IncidentResponse DTO — no sensitive data exposed in responses
- 6 unit tests covering Service layer

### v3.0 — Swagger/OpenAPI
- Swagger UI available at /swagger-ui.html
- OpenAPI documentation auto-generated from controllers
- JWT Bearer authentication integrated in Swagger UI
- All endpoints documented with request/response schemas

## License
This project is licensed under the MIT License.

## 👨‍💻 Author

**Valerio Romita**  
Building Java expertise from fundamentals to enterprise frameworks.

[GitHub](https://github.com/VROMITA) | [LinkedIn](https://www.linkedin.com/in/valerio-romita)
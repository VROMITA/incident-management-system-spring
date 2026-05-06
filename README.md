# Incident Management System (IMS) — Spring Boot REST API

A RESTful API for managing IT incidents, built with Spring Boot and PostgreSQL.
This project is part of a portfolio demonstrating backend Java development skills,
including a deliberate progression from raw JDBC (CLI version) to Spring Boot + JPA.

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

## 👨‍💻 Author

**Valerio Romita**  
Building Java expertise from fundamentals to enterprise frameworks.

[GitHub](https://github.com/VROMITA) | [LinkedIn](https://www.linkedin.com/in/valerio-romita)
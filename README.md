# Zorfin

A secure, role-based **Finance Dashboard System** built with **Spring Boot** that provides:

- JWT-based authentication
- User management (admin/analyst/user roles)
- Transaction management with filtering, pagination, and sorting
- Dashboard analytics (income/expense summaries + grouped stats)
- OpenAPI/Swagger documentation

---

## Table of Contents

1. [Project Overview](#project-overview)
2. [Core Features](#core-features)
3. [Tech Stack](#tech-stack)
4. [Architecture Overview](#architecture-overview)
5. [Security Model](#security-model)
6. [Data Model](#data-model)
7. [API Overview](#api-overview)
8. [Setup & Installation](#setup--installation)
9. [Configuration](#configuration)
10. [Run the Application](#run-the-application)
11. [Default Admin Seeding](#default-admin-seeding)
12. [API Usage Flow](#api-usage-flow)
13. [Error Handling](#error-handling)
14. [Assumptions](#assumptions)
15. [Trade-offs Considered](#trade-offs-considered)
16. [Future Improvements](#future-improvements)

---

## Project Overview

**Zorfin** is a backend service for finance operations and reporting.  
It supports secure access to transaction and user resources with fine-grained role authorization and exposes a dashboard endpoint for high-level financial insights.

The API is designed around:

- **Stateless authentication** using JWT
- **Role-based access control** (`ADMIN`, `ANALYST`, `USER`)
- **Soft deletion** for key domain entities
- **Structured validation and exception handling**

---

## Core Features

- ✅ JWT login (`/api/auth/login`)
- ✅ Role-based authorization with method-level guards (`@PreAuthorize`)
- ✅ User management (CRUD + search filters + role/status updates)
- ✅ Transaction management (CRUD + search filters + pagination/sorting)
- ✅ Dashboard summary endpoint with optional date range analytics
- ✅ Swagger/OpenAPI integration
- ✅ Default admin account seeding at startup
- ✅ Global exception handling with consistent error response shape

---

## Tech Stack

- **Java 21**
- **Spring Boot 3.5.x**
- Spring Web
- Spring Data JPA
- Spring Security
- Bean Validation (Jakarta Validation)
- **MySQL** (via MySQL Connector/J)
- **JWT** (`jjwt`)
- **ModelMapper**
- **Springdoc OpenAPI (Swagger UI)**
- **Lombok**
- Maven

---

## Architecture Overview

Layered architecture:

- **Controller Layer**  
  Exposes REST endpoints and request/response contracts.

- **Service Layer**  
  Contains business rules (validation beyond DTO, update rules, role/status behavior, dashboard aggregation logic).

- **Repository Layer**  
  Uses Spring Data JPA repositories + specifications for filtering.

- **Security Layer**  
  Handles JWT generation, validation, request filtering, and authentication integration.

- **Exception Layer**  
  Centralized exception translation to consistent API errors.

---

## Security Model

Authentication & authorization are implemented using Spring Security + JWT:

- `POST /api/auth/login` is public.
- JWT is required for protected endpoints (`Authorization: Bearer <token>`).
- Stateless session policy (`SessionCreationPolicy.STATELESS`).
- Custom JWT filter runs before `UsernamePasswordAuthenticationFilter`.
- Method-level role checks via `@PreAuthorize`.

### Roles

- `ADMIN`
  - Full user and transaction control
  - Can create/update/delete users and transactions
  - Can change user roles/status (with self-protection rules)

- `ANALYST`
  - Read access to users and transactions
  - Access to dashboard summaries

- `USER`
  - Dashboard read access (as currently configured on dashboard method-level guard)

---

## Data Model

### User
Fields:
- `id`
- `name`
- `email` (unique)
- `password` (encoded with BCrypt)
- `role` (`ADMIN`, `ANALYST`, `USER`)
- `status` (`ACTIVE`, `INACTIVE`)
- `createdAt`, `updatedAt`, `deletedAt`

Behavior:
- Soft-delete enabled (`deleted_at` set instead of hard delete)
- Filtered from queries via Hibernate SQL restriction

### Transaction
Fields:
- `id`
- `title`
- `amount`
- `type` (`INCOME`, `EXPENSE`)
- `category` (enum set such as `SALARY`, `TRAVEL`, etc.)
- `description`
- `createdAt`, `updatedAt`, `deletedAt`

Behavior:
- Soft-delete enabled
- Supports dynamic filtering via specification

---

## API Overview

> Base path examples are shown without host (e.g., `/api/...`).

### Auth

- `POST /api/auth/login`  
  Authenticates user and returns JWT token.

Request:
```json
{
  "email": "admin@example.com",
  "password": "yourPassword"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

### Dashboard

- `GET /api/dashboard?startDate=<ISO_INSTANT>&endDate=<ISO_INSTANT>`

Returns:

- `totalIncome`
- `totalExpense`
- `netBalance`
- grouped `typeStats`
- grouped `categoryStats`

Date parameters are optional.

---

### Users (`/api/users`)

- `POST /api/users` *(ADMIN)*
- `GET /api/users/{id}` *(ANALYST, ADMIN)*
- `GET /api/users` *(ANALYST, ADMIN; paginated/filterable)*
- `PUT /api/users/{id}` *(ADMIN)*
- `DELETE /api/users/{id}` *(ADMIN)*
- `PATCH /api/users/{id}/role` *(ADMIN)*
- `PATCH /api/users/{id}/status` *(ADMIN)*

Filtering supports fields from `UserSearchRequest`:
- `name`, `email`, `role`, `status`

Pagination/sorting:
- `page`, `size`, `sortBy`, `sortDir`

---

### Transactions (`/api/transactions`)

- `POST /api/transactions` *(ADMIN)*
- `GET /api/transactions/{id}` *(ANALYST, ADMIN)*
- `GET /api/transactions` *(ANALYST, ADMIN; paginated/filterable)*
- `PUT /api/transactions/{id}` *(ADMIN)*
- `DELETE /api/transactions/{id}` *(ADMIN)*

Filtering supports fields from `TransactionSearchRequest`:
- `title`, `type`, `category`, `startDate`, `endDate`, `minAmount`, `maxAmount`

Pagination/sorting:
- `page`, `size`, `sortBy`, `sortDir`

---

### Swagger / OpenAPI

- `/swagger-ui/index.html`
- `/v3/api-docs`

---

## Setup & Installation

### Prerequisites

- Java 21
- Maven 3.9+
- MySQL 8+

### 1) Clone repository

```bash
git clone https://github.com/TalhahTahir/zorfin.git
cd zorfin
```

### 2) Configure database and app properties

Create/update your `src/main/resources/application.properties` (or `.yml`) with required values:

```properties
# App
server.port=8080

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/zorfin_db?createDatabaseIfNotExist=true
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true

# Default Admin Seeder
admin.default-email=admin@zorfin.com
admin.default-password=admin12345
```

> For production, use environment variables / secret manager for sensitive values.

---

And make sure you typed correct creds. for DB!
(that's the mistake I made most frequently😅)

## Configuration

Current key runtime configuration behaviors inferred from code:

- JWT token expiration: **30 minutes**
- Password encoding: **BCrypt**
- Session mode: **Stateless**
- Soft delete enabled for users and transactions
- OpenAPI configured with bearer auth scheme name `bearerAuth`

---

## Run the Application

Using Maven:

```bash
mvn spring-boot:run
```

Or build + run jar:

```bash
mvn clean package
java -jar target/zorfin-0.0.1-SNAPSHOT.jar
```

---

## Default Admin Seeding

On startup, `DatabaseSeeder` checks if any `ADMIN` exists.

- If none exists, a default admin user is created using:
  - `admin.default-email`
  - `admin.default-password`

This enables first-time access bootstrap without manual DB inserts.

---

## API Usage Flow

1. Start app + database.
2. Login with seeded admin credentials:
   - `POST /api/auth/login`
3. Copy JWT token from response.
4. Add header to subsequent protected requests:
   - `Authorization: Bearer <token>`
5. Use Swagger UI for endpoint exploration and testing.
(http://localhost:8080/swagger-ui/index.html)

---

## Error Handling

All major exceptions are mapped centrally and return structured responses:

- `400 Bad Request` (validation failures, illegal arguments)
- `401 Unauthorized` (missing/invalid auth)
- `403 Forbidden` (insufficient permissions)
- `404 Not Found` (resource missing)
- `409 Conflict` (duplicate/exists conditions)
- `500 Internal Server Error` (unexpected failures)

Validation errors include per-field details in `fieldErrors`.

---

## Assumptions

The following assumptions were made while preparing this README (based on attached project code):

1. **Primary persistence is MySQL** (as dependency is present and no alternate DB profile was provided).
2. **Configuration files are environment-specific** and not fully attached, so sample properties are provided as a safe baseline.
3. **No refresh token mechanism** exists currently; single JWT lifecycle is used.
4. **Frontend is out of scope**; this repository appears backend-only.
5. **Soft deletes are intentional business behavior** for users/transactions audit safety.
6. **Date query params use ISO-8601 instants** for `Instant` mapping in controllers.

---

## Trade-offs Considered

1. **JWT secret handling**
   - Current implementation uses a hardcoded secret in code (simple for local development). Not acceptable in production

---

## Future Improvements

- Externalize JWT secret and expiration config
- Add refresh-token flow
- Add Flyway/Liquibase migrations
- Add Docker/Docker Compose setup
- Add unit/integration tests for security rules and services
- Add audit logging for sensitive admin actions
- Add rate limiting for auth endpoints
- Richer Dashboard stats by adding trends
- Using MapStruct for efficient Scalability
- Additional Exception Handling
  (Like: Invalid JSON format)

---

Thank you for your time and for appriciating this project 😊
Regards,
Talha Tahir
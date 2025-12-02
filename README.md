# Alert-City Backend

REST API for urban problem reporting and civic engagement platform.

## Tech Stack

Java 17 • Spring Boot 3.5.7 • PostgreSQL • JWT • Swagger

## Quick Start

```
# Configure database in application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/<your-database>
spring.datasource.username=<your-username>
spring.datasource.password=<your-password>

# Run
mvn spring-boot:run

# API Documentation
http://localhost:8080/swagger-ui/index.html
```

## Features

- User authentication (JWT)
- Report urban problems with geolocation
- Categories with SLA tracking
- Comments and file uploads
- Role-based access (CITIZEN, MANAGER, ADMIN)

## API Endpoints

**Public:**
- `GET /occurrences` - List reports
- `GET /categories` - List categories
- `POST /auth/register` - Register
- `POST /auth/login` - Login

**Authenticated:**
- `POST /occurrences` - Create report
- `PATCH /occurrences/{id}/status` - Update status
- `POST /comments` - Add comment
- `POST /attachments/upload` - Upload file

## Security

- BCrypt password encryption
- JWT authentication
- User privacy protection
- Public read, authenticated write
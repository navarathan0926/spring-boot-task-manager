# Task Manager API

## Overview
A simple CRUD (Create, Read, Update, Delete) Task Management API built with Spring Boot and JPA.

## Table of Contents
- [Project Setup](#project-setup)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Sample API Requests & Responses](#sample-api-requests--responses)
- [Contributing](#contributing)
- [License](#license)

## Project Setup

### Prerequisites
Ensure you have the following installed:
- [Java 17+](https://adoptopenjdk.net/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [MySQL](https://www.mysql.com/) or [PostgreSQL](https://www.postgresql.org/)
- [Maven](https://maven.apache.org/)

### Installation

Clone the repository:
```sh
git clone https://github.com/your-repo/task-manager.git
cd task-manager
```

Build the project:
```sh
mvn clean install
```

### Environment Configuration
Create an `application.properties` file inside `src/main/resources/` and configure the database connection:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/task_manager
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### Running the Project
To start the application:
```sh
mvn spring-boot:run
```

## API Documentation

To test the API endpoints, import the following Postman collection:

[Download Postman Collection](task-manager-sprin-boot.postman_collection)

### Import Instructions:
1. Download the `task-manager-sprin-boot.postman_collection` file.
2. Open Postman and go to **File > Import**.
3. Select the downloaded file to import the collection.
4. Use the available requests to interact with the API.

Alternatively, if Swagger is enabled, access it via:
```
http://localhost:8080/swagger-ui.html
```

## Database Schema

### Tables

#### `task`
| Column      | Type        | Description                  |
|------------|------------|------------------------------|
| id         | BIGINT     | Primary key (Auto-increment) |
| title      | VARCHAR    | Task title                   |
| completed  | BOOLEAN    | Task completion status       |
| created_at | TIMESTAMP  | Task creation timestamp      |
| updated_at | TIMESTAMP  | Task last update timestamp   |

## Sample API Requests & Responses

### 1. Create a Task
**Request:**
```sh
POST /api/tasks
Content-Type: application/json
```
**Body:**
```json
{
  "title": "Finish project",
  "completed": false
}
```

**Response:**
```json
{
  "id": 1,
  "title": "Finish project",
  "completed": false,
  "created_at": "2025-03-22T10:00:00Z",
  "updated_at": "2025-03-22T10:00:00Z"
}
```

### 2. Fetch All Tasks
**Request:**
```sh
GET /api/tasks
```

**Response:**
```json
[
  {
    "id": 1,
    "title": "Finish project",
    "completed": false,
    "created_at": "2025-03-22T10:00:00Z",
    "updated_at": "2025-03-22T10:00:00Z"
  }
]
```

### 3. Update a Task
**Request:**
```sh
PUT /api/tasks/1
Content-Type: application/json
```
**Body:**
```json
{
  "title": "Finish project",
  "completed": true
}
```

**Response:**
```json
{
  "id": 1,
  "title": "Finish project",
  "completed": true,
  "created_at": "2025-03-22T10:00:00Z",
  "updated_at": "2025-03-22T11:00:00Z"
}
```

### 4. Delete a Task
**Request:**
```sh
DELETE /api/tasks/1
```
**Response:**
```json
{
  "message": "Task deleted successfully."
}
```

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Commit changes (`git commit -m "Add new feature"`).
4. Push to the branch (`git push origin feature-branch`).
5. Open a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.


# 📝 TODO Application

A Spring Boot REST API for managing personal todos with deadline tracking and performance analysis.

## ✨ Features

- **Create Todos**: Add new tasks with title, description, and date tracking
- **Track Deadlines**: Set tentative and actual start/end dates
- **Status Management**: Track task status (Scheduled, In Progress, Completed)
- **Vibe Analysis**: Automatic calculation of task performance metrics
    - ✅ On Time
    - 🚀 Before Time
    - ⏰ Delayed (with days calculation)
    - ⏳ Pending
- **CRUD Operations**: Full Create, Read, Update, Delete functionality
- **Bulk Operations**: Delete all todos at once

## 🏗️ Tech Stack

- **Framework**: Spring Boot 3.5.4
- **Java**: 17
- **Database**: JPA/Hibernate with default in-memory DB
- **Build Tool**: Maven
- **Additional Libraries**:
    - Lombok (for boilerplate reduction)
    - Spring Web (REST API)
    - Spring Data JPA (ORM)

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven 3.6+

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/kirthikm-24/todo.git
   cd todo-application
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```
   The API will be available at `http://localhost:8080`

## 📚 API Endpoints

### Get All Todos

```http
GET /todos
```

**Response**: List of all todos with vibe analysis

```json
[
  {
    "id": 1,
    "name": "Design database schema",
    "description": "Create ERD for todo app",
    "tentativeEndDate": "2024-01-15",
    "actualEndDate": "2024-01-14",
    "vibe": "DONE BEFORE TIME",
    "status": "COMPLETED"
  }
]
```

---

### Get Todo by ID

```http
GET /todos/{id}
```

**Parameters**:

- `id` (path) - Todo ID

**Response**: Single todo with vibe analysis

---

### Create Todo

```http
POST /todos
Content-Type: application/json
```

**Request Body**:

```json
{
  "name": "Implement API endpoints",
  "description": "Create REST endpoints for todo management",
  "tentativeStartDate": "2024-01-10",
  "tentativeEndDate": "2024-01-15",
  "actualStartDate": "2024-01-10",
  "actualEndDate": null,
  "status": "IN_PROGRESS"
}
```

**Response**: `201 Created` with created todo

---

### Update Todo

```http
PUT /todos/{id}
Content-Type: application/json
```

**Parameters**:

- `id` (path) - Todo ID

**Request Body**: Same as Create Todo

**Response**: Updated todo with vibe analysis

---

### Delete Todo

```http
DELETE /todos/{id}
```

**Parameters**:

- `id` (path) - Todo ID

**Response**: `204 No Content`

---

### Delete All Todos

```http
DELETE /todos
```

**Response**: `204 No Content`

## 📊 Vibe Calculation

The system automatically calculates a "vibe" for each todo based on task completion:

| Condition                             | Vibe                     |
|---------------------------------------|--------------------------|
| `actualEndDate` is null               | PENDING                  |
| `tentativeEndDate` is null            | DONE (No tentative date) |
| `actualEndDate` == `tentativeEndDate` | DONE ON TIME             |
| `actualEndDate` < `tentativeEndDate`  | DONE BEFORE TIME         |
| `actualEndDate` > `tentativeEndDate`  | DELAYED BY X days        |

## 📁 Project Structure

```
todo-application/
├── src/main/java/com/todoapp/
│   ├── controller/
│   │   └── TaskController.java          # REST endpoints
│   ├── service/
│   │   └── TaskService.java             # Business logic
│   ├── mapper/
│   │   └── TaskMapper.java              # Entity ↔ DTO conversion
│   ├── model/
│   │   └── Task.java                    # Entity model
│   ├── dto/
│   │   ├── CreateTaskRequest.java       # Create request DTO
│   │   ├── UpdateTaskRequest.java       # Update request DTO
│   │   └── TaskResponse.java            # Response DTO
│   ├── repository/
│   │   └── TaskRepository.java          # Data access layer
│   ├── util/
│   │   └── TaskUtil.java                # Utility functions
│   ├── exception/
│   │   └── NotFoundException.java        # Custom exception
│   └── Application.java                 # Main entry point
├── pom.xml                              # Maven configuration
└── README.md                            # This file
```

## 🔄 Architecture Overview

The application follows a clean layered architecture:

```
Request → Controller → Service → Repository → Database
            ↓          ↓
         Mapper    Utility
         (DTO)    (Logic)
```

### Key Responsibilities

- **Controller**: Handles HTTP requests/responses
- **Service**: Contains business logic and orchestrates operations
- **Mapper**: Converts between entities and DTOs
- **Repository**: Manages database persistence
- **Util**: Pure utility functions (e.g., vibe calculation)

## 🧪 Testing

To test the API, you can use the provided `requests.http` file or any HTTP client like:

- Postman
- cURL
- REST Client (VS Code extension)

Example with cURL:

```bash
# Get all todos
curl http://localhost:8080/todos

# Create a new todo
curl -X POST http://localhost:8080/todos \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Learn Spring Boot",
    "description": "Complete Spring Boot course",
    "tentativeEndDate": "2024-02-01",
    "status": "IN_PROGRESS"
  }'
```

## 📝 Status Types

- `SCHEDULED`: Task is scheduled but not started
- `IN_PROGRESS`: Task is currently being worked on
- `COMPLETED`: Task is finished

## 🔧 Configuration

Default configuration can be found in `application.properties`. To customize:

1. Create `application-local.properties` for local overrides
2. Set database connection details if using external DB
3. Configure server port (default: 8080)

## 🤝 Contributing

Feel free to submit issues and enhancement requests!

## 📄 License

This project is open source and available under the MIT License.

## 👨‍💻 Author

Created with ❤️ by Kirthik M

---

**Last Updated**: 2026

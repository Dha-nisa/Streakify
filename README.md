# Streakify – Habit Tracking REST API

Streakify is a Spring Boot REST API that helps users track daily habits, maintain streaks, and monitor consistency through a dashboard.

The system allows users to create habits, log daily progress, calculate streaks, and analyze weekly consistency using a PostgreSQL database.

---

# Project Overview

Maintaining habits requires consistency and motivation. Many people struggle to track their daily progress effectively.

Streakify solves this problem by providing a backend system that:

• Tracks user habits  
• Logs daily habit completion  
• Calculates streaks automatically  
• Shows dashboard analytics  
• Prevents duplicate daily logs  

The project focuses on REST API design, database relationships, validation, and exception handling.

---

# Key Features

• User management  
• Habit creation and deletion  
• Daily habit logging  
• Current streak and longest streak calculation  
• Dashboard analytics  
• Weekly consistency score  

---

# Tech Stack

Backend
- Java
- Spring Boot
- Spring Data JPA
- Hibernate

Database
- PostgreSQL

Build Tool
- Maven

API Testing
- Postman

---

# System Architecture
```

Client (Postman / Frontend)
        │
        ▼
 Controller Layer
        │
        ▼
 Service Layer
 (Business Logic)
        │
        ▼
 Repository Layer
 (Spring Data JPA)
        │
        ▼
 PostgreSQL Database

```

# Deliverables

This repository includes:

• GitHub repository with complete source code  
• SQL schema file  
• Postman collection for API testing  
• README documentation including  
  - Setup steps  
  - Database configuration  
  - Sample API requests and responses  
  - Screenshots  

---

## Database Schema

The application uses a PostgreSQL database named **streakify_db** with three main tables:

- users
- habits
- habit_logs

These tables maintain relationships to track users, their habits, and daily habit logs.

---

### Users Table

| Column Name | Data Type |
|-------------|-----------|
| id | BIGSERIAL (PRIMARY KEY) |
| name | VARCHAR(100) (NOT NULL) |
| email | VARCHAR(150) (UNIQUE, NOT NULL) |
| created_at | TIMESTAMP (DEFAULT CURRENT_TIMESTAMP) |

---

### Habits Table

| Column Name | Data Type |
|-------------|-----------|
| id | BIGSERIAL (PRIMARY KEY) |
| name | VARCHAR(100) (NOT NULL) |
| target_days_per_week | INT (NOT NULL) |
| user_id | BIGINT (NOT NULL, FOREIGN KEY → users(id), ON DELETE CASCADE) |
| created_at | TIMESTAMP (DEFAULT CURRENT_TIMESTAMP) |

---

### Habit Logs Table

| Column Name | Data Type |
|-------------|-----------|
| id | BIGSERIAL (PRIMARY KEY) |
| habit_id | BIGINT (NOT NULL, FOREIGN KEY → habits(id), ON DELETE CASCADE) |
| log_date | DATE (NOT NULL) |
| completed | BOOLEAN (NOT NULL) |

Additional Constraint:

- UNIQUE (habit_id, log_date) → ensures only one log per habit per day

---

### Table Relationships

```
users (1) ──────── (many) habits  
habits (1) ─────── (many) habit_logs
```

- A **user can create multiple habits**
- Each **habit can have multiple daily logs**
- A **unique constraint prevents duplicate logs for the same day**

# API Endpoints

User APIs

POST /users  
GET /users/{id}

Habit APIs

POST /habits  
GET /users/{userId}/habits  
DELETE /habits/{id}

Habit Logs

POST /habits/{habitId}/logs  
PUT /habits/{habitId}/logs/{date}  
GET /habits/{habitId}/logs

Streak

GET /habits/{habitId}/streak

Dashboard

GET /users/{userId}/dashboard

---

# Setup Steps

Follow the steps below to run the **Streakify backend application** locally.

### 1. Clone the Repository

```bash
git clone https://github.com/Dha-nisa/Streakify.git
```

### 2. Navigate to the Project Directory

```bash
cd Streakify
```

### 3. Configure PostgreSQL Database

Create a database:

```sql
CREATE DATABASE streakify_db;
```

Update the **application.properties** file:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/streakify_db
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 4. Install Project Dependencies

```bash
mvn clean install
```

This command downloads all required dependencies and builds the project.

### 5. Run the Spring Boot Application

```bash
mvn spring-boot:run
```

Or run the main class from your IDE:

```
StreakifyApplication.java
```

Once the application starts successfully, it will run at:

```
http://localhost:8080
```

# Sample API Requests

Create User

POST /users

{
  "name": "John",
  "email": "john@example.com"
}

Create Habit

POST /habits

{
  "name": "Workout",
  "targetDaysPerWeek": 5,
  "userId": 1
}

Log Habit Completion

POST /habits/{habitId}/logs

{
  "logDate": "2026-03-12",
  "completed": true
}

Dashboard Example Response

GET /users/{userId}/dashboard

{
  "totalHabits": 2,
  "activeHabits": 2,
  "completedToday": 1,
  "currentStreaks": [],
  "consistencyScore": 50
}

---

# Project Structure
```
streakify
│
├── controller
│   ├── DashboardController
│   ├── HabitController
│   ├── HabitLogsController
│   ├── StreakController
│   └── UserController
│
├── service
│   ├── DashboardService
│   ├── HabitService
│   ├── HabitLogService
│   ├── StreakService
│   └── UserService
│
├── repository
│   ├── HabitRepository
│   ├── HabitLogsRepository
│   └── UserRepository
│
├── model
│   ├── Habit
│   ├── HabitLog
│   └── Users
│
├── dto
│   ├── HabitRequestDto
│   ├── HabitResponseDto
│   ├── HabitLogRequestDto
│   ├── HabitLogResponseDto
│   ├── DashboardResponseDto
│   ├── StreakInfoDto
│   └── UserResponseDto
│
└── exceptionHandling
    ├── GlobalExceptionHandler
    ├── ResourceNotFoundException
    └── BadRequestException

```

# Screenshots

Add screenshots such as:

• Postman API testing  
• Database tables in pgAdmin  
• Dashboard API response  

Example folder

docs/screenshots/

---

# Author

Dhanisa R

GitHub  
https://github.com/Dha-nisa

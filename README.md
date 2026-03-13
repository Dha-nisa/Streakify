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

Client (Postman)
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

### Create User

<img width="1438" height="698" alt="Screenshot 2026-03-11 171201" src="https://github.com/user-attachments/assets/25c6d5e2-e355-4922-a166-c445e4c3d704" />

### Get User

<img width="1459" height="690" alt="Screenshot 2026-03-11 171607" src="https://github.com/user-attachments/assets/be3265ec-de28-4230-a7a8-eacba9ccb95a" />

### Delete User

<img width="1455" height="611" alt="Screenshot 2026-03-11 171810" src="https://github.com/user-attachments/assets/6821a9af-f048-41bc-82bc-2027b885e172" />

### Create Habit

<img width="1451" height="688" alt="Screenshot 2026-03-11 172511" src="https://github.com/user-attachments/assets/7997fbd1-307a-4821-a83c-989ac658ffc7" />

### Get User Habit

<img width="1451" height="953" alt="Screenshot 2026-03-11 173817" src="https://github.com/user-attachments/assets/e0568f02-aeee-4d57-beda-c3bd9da05abc" />

### Delete Habit

<img width="1450" height="780" alt="Screenshot 2026-03-12 102813" src="https://github.com/user-attachments/assets/0de59144-0685-4b91-aac9-51f293830540" />

### Habit Log Day1

<img width="1454" height="823" alt="Screenshot 2026-03-12 104617" src="https://github.com/user-attachments/assets/93902072-68d1-48be-9f31-656891232948" />

### Habit Log Day2

<img width="1451" height="790" alt="image" src="https://github.com/user-attachments/assets/3348fd74-7314-45a0-9aab-36bca8027ddd" />

### Habit Log Day3


<img width="1450" height="819" alt="image" src="https://github.com/user-attachments/assets/be93dd19-80c3-4742-883a-30e09ee65561" />


### Habit Log Day4

<img width="1444" height="867" alt="image" src="https://github.com/user-attachments/assets/b12e6f6c-0113-46b3-b879-d9bda9d12511" />

### Fetch Streak


<img width="1446" height="763" alt="image" src="https://github.com/user-attachments/assets/d7341f11-c876-4e40-8282-dbcd91ffbd5c" />

### Dashboard

<img width="1456" height="1002" alt="Screenshot 2026-03-12 124644" src="https://github.com/user-attachments/assets/248e71f2-771d-41f9-a11d-b86dd7faf064" />

## Negative Cases

### Duplicate Email

<img width="1434" height="617" alt="Screenshot 2026-03-11 171338" src="https://github.com/user-attachments/assets/0ac3521b-9ffc-4980-9b7f-643adda0c321" />


### Invalid Email


<img width="1455" height="707" alt="Screenshot 2026-03-11 171447" src="https://github.com/user-attachments/assets/c30c111e-9dce-4989-8269-6e3179b821d5" />

### User Not Found

<img width="1453" height="723" alt="Screenshot 2026-03-11 171653" src="https://github.com/user-attachments/assets/40ec7a31-dcbc-491d-b259-a67b8eec2fa3" />

### User Not Found With Habit Id


<img width="1440" height="742" alt="image" src="https://github.com/user-attachments/assets/543f4439-a202-4fb4-81e1-e49578e61c33" />

### Deleting a Non Exsisting Habit


<img width="1451" height="783" alt="image" src="https://github.com/user-attachments/assets/193d347e-e277-47d1-b5ab-0915a61fb1de" />

### Future Date Not Allowed


<img width="1451" height="826" alt="Screenshot 2026-03-12 105429" src="https://github.com/user-attachments/assets/91bc2f01-682d-4921-845c-03a592620ebd" />


### Habit Not Found


<img width="1443" height="733" alt="image" src="https://github.com/user-attachments/assets/6e97e777-a1b2-4a50-b881-2db369e10428" />


### Duplicate Logs


<img width="1453" height="829" alt="Screenshot 2026-03-12 105154" src="https://github.com/user-attachments/assets/2de9c487-774a-45e8-a7d2-72969190a42f" />


---

# Author

Dhanisa R

GitHub  
https://github.com/Dha-nisa

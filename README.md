# Employee Management System

A comprehensive Spring Boot application for managing employees within an organization with JWT authentication.

## Features

- **Authentication**:
  - User registration and login
  - JWT token-based authentication
  - Role-based access control

- **Employee Management**:
  - Create, read, update, and delete employees
  - Activate/deactivate employee accounts
  - Assign employees to managers
  - Assign roles to employees (EMPLOYEE, MANAGER, ADMIN)
  - View employees by manager

## Technologies Used

- **Java 21**: Core programming language
- **Spring Boot 3.4.5**: Application framework
- **Spring Data JPA**: Data persistence
- **Spring Security**: Authentication and authorization
- **JWT**: JSON Web Token authentication
- **MySQL**: Database
- **Lombok**: Reduces boilerplate code
- **Maven**: Dependency management and build tool

## Database Schema

### Employees Table
- id (PK)
- first_name
- last_name
- email (unique)
- phone_number
- hire_date
- salary
- manager_id (FK, self-referential)
- role (ENUM: EMPLOYEE, MANAGER, ADMIN)
- active (boolean)

### Users Table
- id (PK)
- username (unique)
- password (encrypted)
- email (unique)
- full_name
- active (boolean)

### User_Roles Table
- user_id (FK)
- role (ENUM: EMPLOYEE, MANAGER, ADMIN)

## Getting Started

### Prerequisites

- Java 21 or higher
- MySQL
- Maven

### Setup

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd ems
   ```

2. **Configure database**:
   
   Update `application.properties` with your database credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/employee_db?createDatabaseIfNotExist=true
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   ```

3. **Build and run**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

   Alternatively, you can use the provided Maven wrapper:
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

4. **Access the application**:
   The application will be available at `http://localhost:8080`

## API Endpoints

### Authentication APIs

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/api/auth/register` | Register a new user | Public |
| POST | `/api/auth/login` | Login and get JWT token | Public |

### User Management APIs

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/users` | Get all users | ADMIN |
| GET | `/api/users/{id}` | Get user by ID | ADMIN or Self |
| GET | `/api/users/username/{username}` | Get user by username | ADMIN or Self |
| PUT | `/api/users/{id}` | Update user | ADMIN or Self |
| DELETE | `/api/users/{id}` | Delete user | ADMIN |

### Employee APIs

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/employees` | Get all employees | Authenticated |
| GET | `/api/employees/{id}` | Get employee by ID | Authenticated |
| POST | `/api/employees` | Create new employee | MANAGER, ADMIN |
| PUT | `/api/employees/{id}` | Update employee | MANAGER, ADMIN |
| DELETE | `/api/employees/{id}` | Delete employee | ADMIN |
| GET | `/api/employees/manager/{managerId}` | Get employees by manager | MANAGER, ADMIN |
| PUT | `/api/employees/{id}/activate` | Activate employee | MANAGER, ADMIN |
| PUT | `/api/employees/{id}/deactivate` | Deactivate employee | MANAGER, ADMIN |
| PUT | `/api/employees/{id}/role/{role}` | Assign role to employee | ADMIN |

## Data Formats

### Authentication

#### Register User Request
```json
{
  "username": "johndoe",
  "email": "john.doe@example.com",
  "password": "securepassword",
  "fullName": "John Doe",
  "roles": ["EMPLOYEE"]
}
```

#### Login Request
```json
{
  "username": "johndoe",
  "password": "securepassword"
}
```

#### Authentication Response
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "johndoe",
  "email": "john.doe@example.com"
}
```

### User Management

#### User Data Format
```json
{
  "id": 1,
  "username": "johndoe",
  "email": "john.doe@example.com",
  "fullName": "John Doe",
  "roles": ["EMPLOYEE"],
  "active": true
}
```

### Employee Management

#### Employee Data Format
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@company.com",
  "phoneNumber": "1234567890",
  "hireDate": "2023-01-15",
  "salary": 75000.00,
  "managerId": 2,
  "managerName": "Jane Smith",
  "role": "EMPLOYEE",
  "active": true
}
```

## Authentication Flow

1. **Register** a new user account:
   ```
   POST /api/auth/register
   ```

2. **Login** to get the JWT token:
   ```
   POST /api/auth/login
   ```

3. **Use the JWT token** for all authenticated requests by including it in the Authorization header:
   ```
   Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
   ```

## Examples

### Register a new user
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123","email":"admin@example.com","fullName":"Admin User","roles":["ADMIN"]}'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

### Create Employee (with JWT authentication)
```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{"firstName":"John","lastName":"Doe","email":"john.doe@company.com","phoneNumber":"1234567890","hireDate":"2023-01-15","salary":75000.00,"role":"MANAGER","active":true}'
```

### Assign Manager
```bash
curl -X PUT http://localhost:8080/api/employees/2 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{"firstName":"Jane","lastName":"Smith","email":"jane.smith@company.com","phoneNumber":"0987654321","hireDate":"2023-02-10","salary":65000.00,"managerId":1,"role":"EMPLOYEE","active":true}'
```

### Assign Role
```bash
curl -X PUT http://localhost:8080/api/employees/2/role/ADMIN \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

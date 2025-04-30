# Employee Management System

A comprehensive Spring Boot application for managing employees and departments within an organization.

## Features

- **Employee Management**:
  - Create, read, update, and delete employees
  - Activate/deactivate employee accounts
  - Assign employees to departments and managers
  - View employees by department or manager

- **Department Management**:
  - Create, read, update, and delete departments
  - Track employee count per department

## Technologies Used

- **Java 21**: Core programming language
- **Spring Boot 3.4.5**: Application framework
- **Spring Data JPA**: Data persistence
- **Spring Security**: Authentication and authorization
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
- department_id (FK)
- manager_id (FK)
- active

### Departments Table
- id (PK)
- name (unique)
- description

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

### Employee APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/employees` | Get all employees |
| GET | `/api/employees/{id}` | Get employee by ID |
| POST | `/api/employees` | Create new employee |
| PUT | `/api/employees/{id}` | Update employee |
| DELETE | `/api/employees/{id}` | Delete employee |
| GET | `/api/employees/department/{departmentId}` | Get employees by department |
| GET | `/api/employees/manager/{managerId}` | Get employees by manager |
| PUT | `/api/employees/{id}/activate` | Activate employee |
| PUT | `/api/employees/{id}/deactivate` | Deactivate employee |

### Department APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/departments` | Get all departments |
| GET | `/api/departments/{id}` | Get department by ID |
| POST | `/api/departments` | Create new department |
| PUT | `/api/departments/{id}` | Update department |
| DELETE | `/api/departments/{id}` | Delete department |

## Example Requests

### Create Department
```json
POST /api/departments
{
  "name": "Engineering",
  "description": "Software development and engineering"
}
```

### Create Employee
```json
POST /api/employees
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@company.com",
  "phoneNumber": "1234567890",
  "hireDate": "2023-01-15",
  "salary": 75000.00,
  "departmentId": 1,
  "active": true
}
```

### Assign Manager
```json
PUT /api/employees/2
{
  "firstName": "Jane",
  "lastName": "Smith",
  "email": "jane.smith@company.com",
  "phoneNumber": "0987654321",
  "hireDate": "2023-02-10",
  "salary": 65000.00,
  "departmentId": 1,
  "managerId": 1,
  "active": true
}
```

## Security

The application currently uses a simplified security configuration that allows all requests. In a production environment, you should implement proper authentication and authorization mechanisms.

# Classroom Management System

A Spring Boot REST API application for managing university classrooms, courses, and users at FST (Faculté des Sciences de Tunis).

## Features

- **User Management**: Add and manage students and administrators
- **Class Management**: Create and organize academic classes by level
- **Course Management**: Manage courses with specialties, hours, and archiving
- **User-Class Assignment**: Assign users to specific classes
- **Analytics**: Count users by level and calculate total hours by specialty
- **Automated Archiving**: Scheduled task to archive courses every 60 seconds
- **REST API**: Full CRUD operations with Swagger documentation

## Technologies

- **Java 21**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **PostgreSQL**
- **Swagger/OpenAPI 3**
- **Maven**
- **Lombok**

## Prerequisites

- Java 21 or higher
- PostgreSQL 12 or higher
- Maven 3.6 or higher

## Database Setup

1. Create a PostgreSQL database:
```sql
CREATE DATABASE classroomdb;
```

2. Copy the template configuration file:
```bash
cp src/main/resources/application.properties.template src/main/resources/application.properties
```

3. Update database credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.password=your_actual_password
```

**Note**: The `application.properties` file is excluded from version control to protect your database credentials.

## Running the Application

1. Clone the repository:
```bash
git clone <your-repo-url>
cd projet
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation

Once the application is running, access the Swagger UI at:
```
http://localhost:8080/swagger-ui.html
```

## Available Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/classroom/utilisateurs` | Add a new user |
| POST | `/api/classroom/classes` | Add a new class |
| POST | `/api/classroom/cours/{codeClasse}` | Add a course to a class |
| PUT | `/api/classroom/utilisateurs/{idUtilisateur}/classes/{codeClasse}` | Assign user to class |
| GET | `/api/classroom/utilisateurs/count?niveau=QUATRIEME` | Count users by level |
| DELETE | `/api/classroom/cours/{idCours}/classe` | Unassign course from class |
| GET | `/api/classroom/cours/heures?specialite=INFORMATIQUE&niveau=QUATRIEME` | Calculate total hours |

## Initial Data

The application automatically populates initial demonstration data on startup:
- **Users**: Amna Ammar (etudiant), Ahmed Slama (admin)
- **Classes**: 4AG1 (QUATRIEME), 5EM1 (CINQUIEME)
- **Courses**: Programmation C (42h), Plantes (25h), Sciences Naturelles (40h)

## Testing

Run all tests:
```bash
mvn test
```

## Project Structure

```
src/
├── main/
│   ├── java/org/example/classroommanagement/
│   │   ├── entities/          # JPA entities
│   │   ├── repositories/      # Spring Data repositories
│   │   ├── services/          # Business logic layer
│   │   ├── controllers/       # REST controllers
│   │   ├── config/            # Configuration classes
│   │   └── exceptions/        # Exception handlers
│   └── resources/
│       └── application.properties
└── test/                      # Unit and integration tests
```

## Enumerations

### Niveau (Academic Level)
- PREMIERE
- DEUXIEME
- TROISIEME
- QUATRIEME
- CINQUIEME

### Specialite (Specialty)
- INFORMATIQUE
- GENIECIVIL
- AGRICULTURE

## License

This project is developed as part of an academic assignment at FST.

## Author

Developed for Spring Boot course at FST (Faculté des Sciences de Tunis)

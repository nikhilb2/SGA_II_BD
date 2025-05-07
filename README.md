# Library Management System

A Spring Boot application for managing library books and Writers with CRUD functionality.

## Overview

This project is a web-based Library Management System built with Spring Boot. It showcases CRUD operations (Create, Read, Update, Delete) for two entities: Writer and Book, which share a one-to-many relationship.

## Features

- Add, view, and update Writers
- Add, view, and update Books
- Filter books by a specific Writer
- Special functionality: Display books with their Writers using an inner join query
- Form validation with error messages
- Responsive design using Bootstrap

## Technology Stack

- Java 17
- Spring Boot 2.7.x
- Spring Data JPA
- Spring MVC
- H2 Database (in-memory)
- JSP for views
- Maven for build and dependency management
- Bootstrap 4.5.2 for styling

## Prerequisites

- JDK 17 or higher
- Maven 3.6.x or higher
- IDE (e.g., IntelliJ IDEA, Eclipse, VS Code)

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/yourusername/library-management-system.git
cd library-management-system
```

### Build and Run

```bash
mvn clean install
mvn spring-boot:run
```

Access the application at `http://localhost:8081`.

### Database

The application uses an in-memory H2 database preloaded with sample data. Access the H2 console at:

```
http://localhost:8081/h2-console
```

Connection details:

- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave blank)

## Project Structure

```
spring-boot-crud-app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── springbootcrudapp/
│   │   │               ├── config/
│   │   │               ├── controller/
│   │   │               ├── dto/
│   │   │               ├── entity/
│   │   │               ├── exception/
│   │   │               ├── repository/
│   │   │               └── service/
│   │   ├── resources/
│   │   └── webapp/
│   │       └── WEB-INF/
│   │           └── views/
│   └── test/
│       └── java/
└── pom.xml
```

## Entity Relationship

- Writer (1) ----< Book (N)
  - An Writer can have multiple Books
  - Each Book is associated with one Writer

## Usage

1. **Home Page**

   - Access options for managing books and Writers.

2. **Managing Writers**

   - View all Writers: Click "View All Writers"
   - Add a new Writer: Click "Add New Writer"
   - Edit an Writer: Click "Edit" next to the Writer
   - View Writer details: Click "View" next to the Writer
   - View books by an Writer: Click "Books" next to the Writer

3. **Managing Books**

   - View all books: Click "View All Books"
   - Add a new book: Click "Add New Book"
   - Edit a book: Click "Edit" next to the book
   - View book details: Click "View" next to the book

4. **Special Feature**
   - Display books with their Writers using an inner join: Click "Books with Writers"

## License

This project is licensed under the MIT License. See the LICENSE file for more details.

## Acknowledgments

- Spring Framework Team
- Bootstrap Team
- H2 Database

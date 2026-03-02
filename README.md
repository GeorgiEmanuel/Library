# Library Management System

A desktop application for library resource management, built with **Java 21** and **JavaFX**. This project implements a layered architecture and adheres to software engineering principles to ensure a maintainable and scalable codebase.

---

## Technical Architecture & Methodologies



### Layered Architecture
The application is structured into distinct layers to ensure separation of concerns:
* **Presentation Layer:** JavaFX-based GUI and Controllers handling user interactions and events.
* **Business Logic Layer (Service):** Contains business logic, validation rules, and coordinates data flow using the Notification pattern for error handling.
* **Data Persistence Layer:** Manages MySQL database interactions.

### Software Design Principles
* **SOLID Principles**
* **Dependency Injection:** Managed through **Component Factories** to ensure modularity and decoupling of services, views, and repositories.
* **Design Patterns:**
    * **Builder:** For controlled construction of complex entities (e.g., `UserBuilder`).
    * **Singleton:** Ensuring unified access to database connection pools and factory instances.
    * **Observer:** Implemented via JavaFX EventHandlers for reactive UI updates.

---

## Key Features

* **User Authentication:** Secure registration and login system with view resetting on logout.
* **Role-Based Access Control (RBAC):** Distinct permissions and views for Admin and User roles.
* **Employee Management:** Full CRUD capabilities for managing library staff.
* **Monthly Reports:** Automated generation of PDF reports using the Service layer.
* **Database Security:** Implementation of prepared statements to prevent SQL injection.
* **DTO (Data Transfer Object):** Used to decouple the UI from the database entities (e.g., `UserDTO`).

---

## Tech Stack

| Component | Technology |
| :--- | :--- |
| **Language** | Java 21 |
| **Framework** | JavaFX |
| **Database** | MySQL |
| **Build Tool** | Gradle |
| **Connectivity** | JDBC |

---

## Setup and Installation

### 1. Database Configuration
Install MySQL and create a dedicated database. Update the `JDBConnectionWrapper` class with your local credentials:

```properties
db.url=jdbc:mysql://localhost:3306/your_database_name
db.user=your_username
db.password=your_password

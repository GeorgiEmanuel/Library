# Library Management System

A desktop application for library resource management, built with **Java 21** and **JavaFX**. This project implements a **layered architecture** and adheres to software engineering principles to ensure a maintainable, scalable, and secure codebase.

---

## Table of Contents

* [Project Overview](#project-overview)
* [Technical Architecture & Methodologies](#technical-architecture--methodologies)
* [Key Features](#key-features)
* [Design Patterns](#design-patterns)
* [Tech Stack](#tech-stack)
* [Setup and Installation](#setup-and-installation)
* [Bootstrap & Database Initialization](#bootstrap--database-initialization)
* [Running the Project](#running-the-project)
* [Testing](#testing)
* [Learning Goals](#learning-goals)

---

## Project Overview

This desktop application allows managing library resources, including **books, employees, and users**, with **secure authentication and role-based access control (RBAC)**. Users can place orders, while admins can manage employees, books, and generate **PDF monthly reports**.

---

## Technical Architecture & Methodologies

### Layered Architecture

* **Presentation Layer:** JavaFX GUI + Controllers handling user interactions and events.
* **Business Logic Layer (Service):** Implements core logic, validation, and notifications for error handling.
* **Data Persistence Layer:** Manages MySQL interactions using JDBC.

### Software Design Principles

* **SOLID Principles** applied throughout the codebase.
* **Dependency Injection:** Managed via Component Factories for modularity.
* **Validation & Error Handling:** Notification pattern provides structured feedback for operations like user registration and book management.

---

## Key Features

* **User Authentication:** Registration and login with **SHA-256 password hashing**.
<p align="center">
<img src="https://github.com/user-attachments/assets/c0183e25-60f3-4054-9c07-77e638a25c00" alt="Library Screenshot" width="714" height="506">
<img width="721" height="550" alt="image" src="https://github.com/user-attachments/assets/a3ba8b7d-fd76-4804-a0e9-7c379cdfcb6a" />
</p>


---
* **Role-Based Access Control (RBAC):** Admin, Employee, and Customer roles with distinct permissions.
<p align="center">
   <img width="1025" height="628" alt="image" src="https://github.com/user-attachments/assets/45b28feb-aa7c-4f2b-a910-5dc3fb62c2a3" />
   <img width="1025" height="628" alt="image" src="https://github.com/user-attachments/assets/f64d4f86-dd7c-4aa5-a487-0e3eca8a02d4" />
   <img width="1025" height="628" alt="image" src="https://github.com/user-attachments/assets/d82a40e0-5eaa-4f6a-8eb6-5b7c428aa671" />
   <img width="1025" height="628" alt="image" src="https://github.com/user-attachments/assets/3485ef30-2980-4e7e-91ba-8914fd66ff86" />
   
</p>

* **Employee Management:** Full CRUD operations on staff accounts.
<p align="center">
   <img width="1019" height="626" alt="image" src="https://github.com/user-attachments/assets/c6cf6cde-b519-43b0-b1dc-4447ced048c0" />
   <img width="1019" height="626" alt="image" src="https://github.com/user-attachments/assets/4e2898e7-59e6-4a43-8e3b-45435be6e308" />
   <img width="1019" height="626" alt="image" src="https://github.com/user-attachments/assets/64be0b65-d95f-4d55-ba73-403eb8afb76a" />



</p>

* **Book Management:** Add, delete, update books, track quantity, and price.
<p align="center">
   <img width="1024" height="628" alt="image" src="https://github.com/user-attachments/assets/e99b53af-2fb7-42b4-88e1-e333bf594167" />
   <img width="1024" height="282" alt="image" src="https://github.com/user-attachments/assets/0ebcfe3c-b364-4c6d-b4ce-b479a61a2452" />
</p>

* **Order Management:** Customers can place orders; inventory quantity is updated automatically.
<p align="center">
   <img width="1022" height="627" alt="image" src="https://github.com/user-attachments/assets/4d1d5442-ca31-4680-89c4-0a17edfb148f" />
   <img width="1022" height="112" alt="image" src="https://github.com/user-attachments/assets/c892a2cb-ff9d-4ad5-917c-c119b64bafc8" />

</p>

* **Monthly Reports:** Automatically generate PDF reports using the Service layer.
<p align="center">
   <img width="1022" height="627" alt="image" src="https://github.com/user-attachments/assets/c962c779-e1d1-49db-bf90-30181a153c5c" />
   <img width="1022" height="400" alt="image" src="https://github.com/user-attachments/assets/bce1f38b-ddc9-488c-a942-7090a9d0fb75" />
   [Report.pdf](https://github.com/user-attachments/files/25817428/Report.pdf)
</p>
* **Database Security:** Prepared statements to prevent SQL injection.
* **DTO (Data Transfer Object):** Decouples UI from database entities for cleaner architecture.


---

## Design Patterns

* **Builder:** For controlled creation of complex entities (`UserBuilder`, `BookDTOBuilder`).
* **Singleton:** Unified access to database connections and component factories.
* **Observer:** JavaFX EventHandlers for reactive UI updates.
* **Decorator:** `BookRepositoryCacheDecorator` for caching repository results.
* **Notification Pattern:** Structured validation and error reporting for service operations.

---

## Tech Stack

| Component        | Technology |
| ---------------- | ---------- |
| **Language**     | Java 21    |
| **Framework**    | JavaFX     |
| **Database**     | MySQL      |
| **Build Tool**   | Gradle     |
| **Connectivity** | JDBC       |

---

## Setup and Installation

### 1. Database Configuration

Install MySQL and create a dedicated database. Update the `JDBConnectionWrapper` class with your credentials:

```properties
db.url=jdbc:mysql://localhost:3306/your_database_name
db.user=your_username
db.password=your_password
```

### 2. Build and Run Application

Build the project using Gradle:

```bash
./gradlew build
```

Run the application (JavaFX GUI):

```bash
./gradlew run
```

---

## Bootstrap & Database Initialization

The project includes a **Bootstrap utility** to initialize roles, rights, and tables:

* `Bootstrap.java` sets up roles, rights, users, and default data.
* `DataBaseConnectionFactory` manages connections for production and test environments.
* Ensures consistent state for development and testing.

---

## Running the Project

1. Start the MySQL database.
2. Run the Bootstrap utility if you need to reset tables and insert default data.
3. Launch the JavaFX application using:

```bash
./gradlew run
```

4. Login with your admin or user credentials.
5. Navigate through the GUI to manage books, employees, and place orders.

---

## Testing

* Unit and integration tests use **JUnit 5**.
* **Mock repositories** are used to isolate service layer testing.
* **Notification pattern** ensures correct validation and error reporting in tests.
* Run all tests:

```bash
./gradlew test
```

* Verify functionality including:

  * User registration and login
  * Role-based access control
  * Book CRUD operations
  * Order processing and quantity updates
  * Monthly report generation

---


## Learning Goals

This project demonstrates:

* Designing a **layered Java desktop application** with JavaFX.
* Implementing **secure authentication** using SHA-256 password hashing.
* Applying **Design Patterns**: Builder, Singleton, Observer, Decorator, Notification.
* Structuring **modular, maintainable code** with Dependency Injection.
* Managing **database connectivity and security** via JDBC and prepared statements.

# Order Management System

This is a Spring Boot based Order Management System developed as a backend project.

## Tech Stack
Java
Spring Boot
Spring Data JPA
Hibernate
MySQL
Maven

## Features
- Customer Management
- Product Management
- Order Creation
- Inventory Handling
- Exception Handling

## Project Structure
Controller Layer
Service Layer
Repository Layer
DTO Layer
Entity Layer

## API Endpoints

### Customer APIs
GET /api/customers
POST /api/customers
GET /api/customers/{id}

### Product APIs
GET /api/products
POST /api/products
GET /api/products/{id}

### Order APIs
POST /api/orders
GET /api/orders/{id}
GET /api/orders

## Architecture

The project follows a layered architecture:

Controller Layer
Handles HTTP requests and responses.

Service Layer
Contains business logic.

Repository Layer
Handles database operations using Spring Data JPA.

DTO Layer
Used to transfer data between layers.

Entity Layer
Represents database tables.

## How to Run the Project

1. Clone the repository

git clone https://github.com/rutuja9325/order-management-system.git

2. Open the project in IntelliJ or Eclipse

3. Configure the database in application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/orderdb
spring.datasource.username=root
spring.datasource.password=yourpassword

4. Run the application

OrderManagementSystemApplication.java

# Order Management System

Backend REST API built with Spring Boot

Java | Spring Boot | Spring Data JPA | MySQL | Maven

## Author
Rutuja Taralkar

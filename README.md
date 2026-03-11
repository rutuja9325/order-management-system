# Order Management System

Backend REST API built with Spring Boot.

This project is a backend REST API built with Spring Boot for managing customers, products, and orders in an Order Management System.

## Tech Stack
- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven 

## Features
- Customer Management
- Product Management
- Order Creation
- Inventory Handling
- Exception Handling

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

## How to Run the Project

1. Clone the repository
2. Open the project in IntelliJ or Eclipse
3. Configure the database in `application.properties`
4. Run the application


## Author
Rutuja Taralkar


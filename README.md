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

## Project Structure

src/main/java/com/ecommerce/oms

- controller – REST API controllers
- service – Business logic
- repository – Database operations
- entity – JPA entities
- dto – Data Transfer Objects
- exception – Global exception handling
- config – Configuration classes

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

## API Request & Response Example

### Create Customer

POST /api/customers

Request Body

{
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "9876543210"
}

Response

{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "9876543210"
}

## How to Run the Project

1. Clone the repository
2. Open the project in IntelliJ or Eclipse
3. Configure the database in `application.properties`
4. Run the application


## Author
Rutuja Taralkar


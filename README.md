# E-Commerce API: Project Overview

## Introduction
Welcome to my e-commerce API! I built this project to learn and understand different concepts of Java and Spring boot. This project is built using **Java 21**, **Spring Boot 3**, **Spring Data JPA**, and **Spring Security**. It provides essential functionalities for managing users, products, orders, addresses, and categories. Here’s an overview of the main features:

## Authentication and Authorization
- Implemented JWT-based login mechanism using **Spring Security**.
- Secured endpoints to prevent unauthorized access.

## Entities and Relationships
- **User**: Represents users with details like first name, last name, email, and password.
- **Product**: Stores product information (name, price, quantity).
- **Order**: Manages orders, including order items and status.
- **OrderItems**: Connects products to orders.
- **Address**: Stores user addresses.
- **Category**: Organizes products into different categories.

## External Image Storage
- Utilized the **ImgBB API** to store and retrieve product images.
- Images are associated with product entities.

## Database Setup
- Configured **PostgreSQL** as the database.
- Dockerized both the Spring Boot app and the PostgreSQL container for easy deployment.

## Endpoints

### Order Management
- `GET /orders`: Retrieves a list of orders.
- `POST /orders`: Creates a new order.
- `PATCH /admin/orders/{orderId}/status`: Updates the order status.
- `GET /orders/{orderId}`: Retrieves details of a specific order.

### Product Management
- `POST /admin/products`: Adds a new product.
- `PATCH /admin/products/{productId}`: Updates product details.
- `PATCH /admin/products/{productId}/quantity`: Adjusts product quantity.
- `PATCH /admin/products/{productId}/images`: Updates the product image.

### Category Management
- `POST /admin/category`: Creates a new product category.
- `DELETE /admin/category/{categoryId}`: Deletes a product category.
- `PATCH /admin/category/{categoryId}`: Updates the product category name.
- `GET /category`: Retrieves a list of product categories.

### Authentication
- `POST /auth/register`: Registers a new user.
- `POST /auth/login`: Logs in a user.

### User Management
- `DELETE /users`: Deletes a user account.
- `PATCH /users`: Updates user details.
- `GET /admin/users`: Retrieves a list of all users.
- `GET /admin/users/{userId}`: Retrieves details of a specific user.

## Things I Could Have Done Better

### Soft-Delete for Consistency
- Implementing soft-delete (logical deletion) for entities would maintain data consistency while allowing for easy recovery or auditing.
- By marking records as “deleted” instead of physically removing them, you can avoid accidental data loss.

### Code Structure and Circular Dependencies
- Structuring the code to avoid circular dependencies is crucial for maintainability.
- Consider using package-by-feature or package-by-layer organization to minimize interdependencies between classes.

### Spring Profiles for Environment-Specific Properties
- Spring profiles allow you to maintain separate properties files for different environments (e.g., development, production, testing).
- By using profiles, you can configure database connections, API keys, and other environment-specific settings more efficiently.

## Step-by-Step Instructions
1. ## Clone the repository from GitHub.
2. ## Build the Project
   Build the project using Maven. This will create the JAR file in the target directory.

   - mvn clean install

   Note: If there are any issues or errors you can ues the following command:
   
   - mvn clean -DskipTests package

4. ## Run Docker Compose

   - docker-compose up --build

5. ## Access the Application
   Once the containers are running, you can access the API at http://localhost:8080.
   Swagger Documentation: Open your browser and go to http://localhost:8080/swagger-ui.html to view the API documentation and test endpoints.
6. ## Stop the Containers
   To stop the containers, use the following command:

   - docker-compose down


## Conclusion
This e-commerce API demonstrates my skills in **Java**, **Spring Boot**, and **API development**. I hope you find it useful.

Feel free to customize the content above to match your project specifics. Good luck with your job search! 🚀

---

Feel free to add any additional sections or details as needed. If you have further requests or need assistance, feel free to ask! 😊

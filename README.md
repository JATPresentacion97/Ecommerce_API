# E-Commerce Application

This is a Spring Boot-based e-commerce application built with Java and managed using Gradle. The application allows users to manage products, shopping carts, user profiles, wishlists, and email notifications.

## Features

- **Product Management**: Create, read, update, and delete products.
- **Shopping Cart**: Add, remove, and clear items in the user's cart.
- **User Profiles**: Manage user profile information including first name, last name, address, and phone number.
- **Wishlist**: Save products for future purchases.
- **Email Notifications**: Send order confirmation, password reset, and promotional emails.
- **User Authentication**: Register, log in, and manage user sessions.

## Technologies Used

- **Java**: The programming language used for development.
- **Spring Boot**: Framework for building the application.
- **Gradle**: Build automation tool.
- **Hibernate/JPA**: For database interactions.
- **MySQL**: Database for storing application data.

## Getting Started

### Prerequisites

Make sure you have the following installed on your machine:

- JDK 17 or later
- Gradle
- MySQL (or any compatible relational database)

### Installation

1. **Clone the repository**:

   ```bash
   git clone https://github.com/yourusername/ecommerce-application.git
   cd ecommerce-application
   ```
2. **Configure the database**:

Create a new database in MySQL.

Update the application.properties file located in src/main/resources/ with your database details:
  ```spring.datasource.url=jdbc:mysql://localhost:3306/yourdatabase
    spring.datasource.username=yourusername
    spring.datasource.password=yourpassword
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
  ```

3. **Build the project**:
   
    ```bash
      ./gradlew build
    ```
4. **Run the application**:

    ```bash
      ./gradlew bootRun
    ```
   
    

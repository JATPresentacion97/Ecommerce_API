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
   The application should start on http://localhost:8080.
# API Documentation

The application exposes several RESTful endpoints:

## User Authentication

- **Register User**: `POST /api/auth/register`
- **Login User**: `POST /api/auth/login`

## Product Management

- **Get All Products**: `GET /api/products`
- **Get Product by ID**: `GET /api/products/{id}`
- **Create Product**: `POST /api/products`
- **Update Product**: `PUT /api/products/{id}`
- **Delete Product**: `DELETE /api/products/{id}`

## Shopping Cart

- **Get Cart**: `GET /api/cart/{username}`
- **Add Product to Cart**: `POST /api/cart/{username}/add`
- **Remove Product from Cart**: `DELETE /api/cart/{username}/remove`
- **Clear Cart**: `POST /api/cart/{username}/clear`

## User Profiles

- **Get User Profile**: `GET /api/user/profile/{username}`
- **Create or Update Profile**: `POST /api/user/profile/{username}`
- **Delete User Profile**: `DELETE /api/user/profile/{username}`

## Product Reviews

- **Get Reviews for Product**: `GET /api/products/{productId}/reviews`
- **Add Review**: `POST /api/products/{productId}/reviews`
- **Delete Review**: `DELETE /api/products/reviews/{reviewId}`

## Contributing

If you want to contribute to this project, please fork the repository and create a new branch for your feature or bug fix.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a pull request

## License

This project is licensed under the MIT License. See the LICENSE file for details.

## Acknowledgments

- [Spring Boot](https://spring.io/projects/spring-boot) - Framework used for this application
- [Gradle](https://gradle.org/) - Build tool used in the project
- [MySQL](https://www.mysql.com/) - Database management system used

    

# 🔄 toShare 🔄

## Overview
toShare is an easy-to-use platform that lets people swap items with each other. It's all about helping you find what you need and share what you don't, all while being part of a community that values reusing and sharing.

## Features
- **Item Listing**: Users can add items they wish to share, providing details and availability.
- **Booking**: Once an item is listed, other users can reserve it for specific periods.
- **Requesting** Items: Users can also make requests for items they are looking for, which can then be fulfilled by other community members.
- **Comments**: Each item has a comment section, enabling users to leave reviews or ask questions about the item.

---

## Technologies Used

### Backend

- **Spring Boot**: Used for creating controllers, services, and repositories, as well as configuring their interactions. It also simplifies the bootstrapping and development of a new Spring application.

- **PostgreSQL**: Serves as the application's database, providing robust, scalable, and well-supported data storage.

- **Spring Data JPA**: Utilized for object-relational mapping through annotations, simplifying the data access layer. It also provides a way to create repository implementations automatically, allowing for more agile and efficient data manipulation.

### Testing

- **JUnit**: The testing framework used for writing unit tests, ensuring code reliability and quality.

- **Mock**: Employed in conjunction with MockMvc for testing the MVC layer of the application, allowing for comprehensive web layer testing.

### Deployment and Containerization

- **Docker**: Used for creating images and running containers that house both the database and the application, ensuring a consistent environment.

### API Documentation and Testing

- **OpenAPI**: The `openapi.yaml` file provides a detailed description of the project's API, facilitating API testing and integration.

- **Postman**: Includes a collection of tests for validating the application's HTTP-based functionalities, ensuring endpoint reliability and correctness.

### Code Efficiency

- **Lombok, MapStruct**: Utilized to reduce boilerplate code, thereby accelerating development time.

### Future Enhancements

- **Spring Security**: Planned for adding robust authentication and authorization mechanisms to secure the application.

- **AOP (Aspect-Oriented Programming)**: Intended for modularizing cross-cutting concerns like logging, security, etc., thereby improving code maintainability and reusability.

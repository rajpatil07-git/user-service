#User Management System (Spring Boot + JWT + Role-Based Access)

#Overview
This project is a backend User Management System built using Spring Boot.

It implements:
  - User registration and login with REST endpoints
  - JWT-based authentication
  - Role-based authorization (ROLE_USER, ROLE_ADMIN)
  - Input validation
  - Global exception handling
  - Consistent API response structure
  - MySQL database integration

#Features
 #Authentication & Authorization
  - Secure JWT-based authentication
  - Role-based access control: ROLE_USER, ROLE_ADMIN

#User Management Operations
Endpoint	Method	Description	Access
  1. /api/auth/register |	POST | Register a new user	Public
  2. /api/auth/login | POST	| Login and receive JWT token	Public
  3. /api/users |	GET	| Fetch all users	ADMIN
  4. /api/users/{id} | GET | Fetch user by ID	USER/ADMIN
  5. /api/users/{id} | DELETE	| Delete a user by ID	ADMIN
     
#Tech Stack
  - Java 17
  - Spring Boot 3.x
  - Spring Security + JWT
  - Hibernate + JPA
  - MySQL Database
  - Maven Build Tool

#Setup Instructions

#Prerequisites Ensure you have:
  - Java 17+
  - Maven
  - MySQL Server running

#Clone Repository
  git clone https://github.com/rajpatil07-git/user-service

#application.properties setup

spring.application.name=user-service
spring.datasource.url=jdbc:mysql://localhost:3306/userdb?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
jwt.secret=ReplaceWithSecretButUseEnvInProd
jwt.expiration=3600000

cd user-service

#Backend Setup (Spring Boot) run spring boot app - mvn clean install mvn spring-boot:run

#Testing the Application

1. Start MySQL and ensure database userdb exists.
2. Run the Spring Boot backend (http://localhost:8080).
3. Use tools like Postman or curl to interact with the endpoints:
4. Register a new user (default role = ROLE_USER)
5. Login with credentials to receive a JWT token.
6. Use the token for authorized requests to /api/users endpoints.
  i. Normal users can view their profile (/users/{id})
 ii. Admin users can view all users (/users) or delete users.

Author
Raj Patil
Panvel, Maharashtra
Email: rajpatil.rp068@gmail.com
phNo - 7028230254

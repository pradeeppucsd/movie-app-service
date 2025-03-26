# Movie Application Service

### Overview

This is a movie application service API that provides essential features required for a mobile or web application. The service includes the following features:

* A list of popular movies.
* The ability to search for movies by title.
* Detailed information about specific movies.

### Tech stack
* Language: Java
* Backend Framework: Spring Boot
* Database: H2
* ORM: JPA
* API Documentation: Swagger
* Testing: JUnit & Mockito
* Authentication: API Key validation using an interceptor

### Prerequisites
1. **Install JDK 21**
- Download from [Oracle JDK 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) or [OpenJDK 21](https://jdk.java.net/21/).
- Set up environment variables (`JAVA_HOME` and `PATH`) if required.
- Verify installation with:
  ```sh
  java -version
  ```
2. **Install Maven 3.8.x**
- Download from [Apache Maven](https://maven.apache.org/download.cgi).
- Extract and set up the `MAVEN_HOME` environment variable.
- Verify installation with:
  ```sh
  mvn -version
  ``` 

### Getting Started

#### Clone the Repository
```
git clone https://github.com/pradeeppucsd/movie-app-service.git
cd movie-app-service
```
#### Update hard coded API_KEY in below file 
```
src/main/java/com/movie/app/config/ApiKeyInterceptor.java
```
Refer below key,
```
private static final String API_KEY = "";
```

#### Configure the Database
Modify src/main/resources/application.properties to configure your preferred database:
```
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=none
```
The default database configuration is set for H2. To switch to PostgreSQL or MySQL, update the URL, driver class name, username, and password accordingly.

#### Run Database Migrations
Ensure Flyway migrations are executed automatically on startup. Migration scripts are located in:
```
src/main/resources/db/migration
```
Use the naming convention:
```
V1__init.sql, V2__add_movies_table.sql, etc.
```

## Build and Run the Application
* Run with Maven
```
mvn clean install
mvn spring-boot:run
```
## H2 database console:
http://localhost:8080/h2-console

## API documentation: 
* Swagger API docs
[movie-service-api.yml](api-docs/movie-service-api.yml)
* Postman collection:
[movie-app.postman_collection.json](api-docs/postman-collection/movie-app.postman_collection.json)
* Swagger UI: Once the application is running, you can access the API at:
http://localhost:8080/swagger-ui/index.html

## Running Tests:
```
mvn test
```

## Deployment
To package the application as a JAR file:
```
mvn clean package
```
Run the JAR file:
```
java -jar target/movie-app-service.jar
```

## Implementation details:
* Java, Maven, Spring Boot Setup: Configured the project with Maven and Spring Boot for easy dependency management and project structure.
* Standard Project Structure: Organized the project into typical packages (controller, service, repository, model) for maintainability. 
* Flyway for Database Migration: Integrated Flyway for database versioning, making schema changes portable and allowing easy database switching (H2 to PostgreSQL/MySQL). 
* In-Memory Database for Development: Used an in-memory database for local development, with seamless database switch via property changes. 
* REST API in Controller: Created RESTful API endpoints for the movie app, adhering to standard conventions. 
* Javadoc for Readability: Added Javadoc to improve code documentation. 
* Google Java Format: Used google-java-format for consistent code styling.

## Possible Enhancements:
* Test Coverage: Add more unit and integration tests for better coverage.
* Dockerfile: Create a Dockerfile for containerization and consistent deployment. 
* Deployment Config: Set up profiles for different environments and automate deployment (CI/CD). 
* Logging & Monitoring: Integrate logging and monitoring tools like Datadog or Spring Boot Actuator.
* Security: Add authentication/authorization (JWT/OAuth2) with Spring Security.

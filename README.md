# Getting Started

### Movie application service

This movie application service API that supports the features needed for a mobile app or web app and it supporting the following features:
* A list of popular movies.
* The ability to search for movies by title.
* Detailed information about specific movies.

### Tech stack

* Language: Java
* Backend Framework: Spring Boot
* Database: MySQL / H2
* ORM: JPA
* API Documentation: Swagger
* Testing: JUnit & Mockito
* Authentication: API Key validation using an interceptor

#### 8. Sample Data (`data.sql`)
```sql
INSERT INTO movies (title, release_date, poster_url, overview, genres, rating, runtime, language) VALUES
('Inception', '2010-07-16', 'inception.jpg', 'A mind-bending thriller', 'Sci-Fi, Action', 8.8, 148, 'English'),
('Interstellar', '2014-11-07', 'interstellar.jpg', 'A journey through space and time', 'Sci-Fi, Drama', 8.6, 169, 'English');
```
## Prerequisites
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
  ## To build and run:

```
mvn clean install spring-boot:run
```
## API end points and documentation refer postman collection:
[api-docs/postman-collection/movie-app.postman_collection.json](api-docs/postman-collection/movie-app.postman_collection.json)

## Implemelementation:
The main goal is write clean, maintainable, and scalable code.
Created different API end point which is related to movie .

* Setup existing project as git project
* Converted to Maven project with Java 21
* Created package structure and move the classes accordingly.
* Added javadoc for more readability.
* Used google-java-format to format the code.

## Possible Enhancements:
* Swagger doc configuration
* We can cover more test cases
* Deployment configuration.


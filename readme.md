<h2 align="center">Explore California</h2>


### Description
Explore California is a Spring Boot application that exposes a REST API for managing tours, packages, tour ratings, and regions of the state of California.
Users can view detailed information about the tours: average rating, price, duration, complexity, and the package in which a particular tour is included and so on.
Registered and logged-in users can also leave feedback and rating about the visited tour.

The project was taken as a basis from the LinkedIn Learning platform and was redesigned, extended and expanded with other components.
It demonstrates how to use Spring MVC, Spring Data JPA, Spring Security, and Spring HATEOAS, and other technologies to build a web service.
All application data is stored on the local MySQL server.


### Main components
* The three-tier architecture, each layer of which performs its own distinct functions.
* The error/exception handling mechanism for catching and handling errors or exceptions which also provides useful feedback to the users and logs the errors for debugging.
* Stateless user authentication and JWT token for authorization.
* Validation of incoming data from the client.
* DTO pattern for transferring data.
* The hypermedia-based structure for making the application easier to use for the clients.
* Business logic and controller tests.

### Technologies
* Java 17
* Spring Boot 3.1.4
* Spring Web MVC
* Spring Data JPA
* Spring Security
* Java Bean Validation (Hibernate Validator)
* Java JWT: JSON Web Token for Java and Android
* Spring HATEOAS
* JUnit 5 and Mockito
* Logback logger
* Build tool: Maven
* Database: MySQL 8.0.33

#### Extra



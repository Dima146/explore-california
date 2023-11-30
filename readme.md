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
<p float="left">
<img alt="get-tour-by-id" title="get-tour-by-id" height="150" src="https://github.com/Dima146/explore-california/assets/87914550/55c60631-b20f-4fc5-8825-563e70d70e6e" width="200"/>
<img alt="credentials-required" title="credentials-required" height="150" src="https://github.com/Dima146/explore-california/assets/87914550/e813633f-3b70-454c-9dd7-0f12abbb0db1" width="200"/>
<img alt="method-not-supported" title="method-not-supported" height="150" src="https://github.com/Dima146/explore-california/assets/87914550/ca96f94d-2bde-4d3a-b85e-9d5aa84d4694" width="200"/>
</p>
<img alt="no-permission" title="no-permission" height="150" src="https://github.com/Dima146/explore-california/assets/87914550/d935ca9b-8e38-4b31-bd0c-c916f6507bc5" width="200"/>
<img alt="create-tour-rating" title="create-tour-rating" height="150" src="https://github.com/Dima146/explore-california/assets/87914550/9b12d2b9-1742-49e0-91ab-306903b261ab" width="200"/>
<img alt="get-package-by-id" title="get-package-by-id" height="150" src="https://github.com/Dima146/explore-california/assets/87914550/05c8970e-f0d5-415f-b81a-7b0e181b1c9e" width="200"/>
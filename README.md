
User API Controller
This repository contains a RESTful API implemented in Java using Spring Boot for managing user information. The API provides endpoints for CRUD operations (Create, Read, Update, Delete) along with additional functionalities such as PATCH, HEAD, and OPTIONS requests.

Endpoints
GET /api/users: Retrieves a list of all users.

GET /api/users/{userId}: Retrieves details of a specific user by ID.

POST /api/users: Adds a new user. Requires a JSON payload with user details.

PUT /api/users/{userId}: Updates an existing user's details. Requires a JSON payload with updated user information.

DELETE /api/users/{userId}: Deletes a user by ID.

PATCH /api/users/{userId}: Partially updates a user's details based on provided fields in the request body.

HEAD /api/users/{userId}: Checks for the existence of a user by ID without fetching the full resource.

OPTIONS /api/users/{userId}: Retrieves the HTTP methods supported for a user resource.

Error Handling

The API returns appropriate HTTP status codes and messages, including 404 Not Found for resources that do not exist.

Input validation is handled using Jakarta Bean Validation (@Valid annotation) for request bodies.

Technologies Used
Spring Framework: For dependency injection and MVC architecture.

Jakarta Bean Validation: For validating input data.

Postman: For unit testing.

Usage:
To use this API, ensure you have Java and Maven installed. Clone the repository, build the project using Maven, and deploy it to your preferred application server.

HERE'S HOW YOU CAN CREATE YOUR OWN API THAT IS JUST LIKE THIS (USER API):
Create a new Spring Boot project:
Use the Spring Initializr to create a new project.
Select dependencies such as Spring Web, Spring Data JPA, Spring Validation, and a database driver (e.g., MySQL).
Create the Entity class:

Create a User entity class:
Define a User class with fields like id, username, password, email, firstName, lastName, etc.
Annotate the class with JPA annotations (@Entity, @Table, @Id, @GeneratedValue, etc.) to map it to a database table.
Add validation annotations (@NotBlank, @Email, @Size, etc.) to enforce data integrity.
Create the Repository Interface:

Create a UserRepository interface:
Define a UserRepository interface that extends JpaRepository<User, Long> (assuming Long as the data type for id).
This interface will provide CRUD operations for the User entity.
Implement the User Service:

Create a UserService class:
Annotate with @Service and inject UserRepository into the service.
Implement methods for CRUD operations: getAllUsers(), getUserById(Long id), createUser(User user), updateUser(Long id, User user), deleteUser(Long id).
Create the User Controller:

Create a UserController class:
Annotate with @RestController and @RequestMapping("/api/users").
Inject UserService into the controller.
Implement REST API endpoints:
@GetMapping("/"): Get all users
@GetMapping("/{id}"): Get a user by ID
@PostMapping("/"): Create a new user
@PutMapping("/{id}"): Update an existing user
@DeleteMapping("/{id}"): Delete a user
Handle Exceptions:

Create a GlobalExceptionHandler class:
Annotate with @RestControllerAdvice.
Implement exception handling methods to manage:
MethodArgumentNotValidException for validation errors.
ResponseStatusException for custom error handling.
Provide appropriate HTTP status codes and error responses.
Configure the Application Properties:

Update application.properties:
Configure database connection details (spring.datasource.url, spring.datasource.username, spring.datasource.password).
Set other properties like spring.jpa.properties.hibernate.dialect for database-specific settings if necessary.

# OOP End-Term Project

The following project represents the complete implementation of the principles of Object-Oriented Programming using the programming language Java. The application will be designed by adhering to all the requirements of the work, including user management, manipulation with books, report creation, and database interaction. It ensures modularity, scalability, and maintainability by using the SOLID principles and design patterns.

---

## Key Features

### User Management
- **User Types**:
  - Regular Users
  - Admin Users
- **Operations**:
  - Add users
  - Block users
  - Manage roles and permissions

### Book Management
- Add and retrieve books
- Handle invalid inputs with exception handling

### Report Generation
- Summarize key application data
- Modularized using `ReportService`

### Database Integration
- Uses a **Singleton Pattern** for database connection management
- SQL-based storage and retrieval via `DatabaseHandler`

---

## Implementation Details

### Object-Oriented Programming Principles
- **Encapsulation**:
  - Private fields and public getters/setters in classes like `User` and `Book`
- **Inheritance**:
  - `AdminUser` extends `User` to represent user roles
- **Polymorphism**:
  - Overridden methods (e.g., `toString` in `AdminUser`)
- **Method Overloading**:
  - Flexible methods, such as `addBook` with different parameter options
- **Exception Handling**:
  - Comprehensive use of `try-catch` blocks for robust error handling

### SOLID Principles
- **Single Responsibility Principle**:
  - Each class handles a single responsibility (e.g., `UserService` for user logic)
- **Open/Closed Principle**:
  - Extensible classes, such as `User` and its derived `AdminUser`
- **Liskov Substitution Principle**:
  - Interchangeable use of base (`User`) and derived (`AdminUser`) classes
- **Interface Segregation Principle**:
  - Specialized interfaces (`IUserService`, `IBookService`) for specific functionalities
- **Dependency Inversion Principle**:
  - High-level modules depend on abstractions using interfaces

### Design Patterns
- **Singleton Pattern**:
  - Ensures a single instance of the `DatabaseHandler` class

---

## Data Management
- **Database**:
  - SQL-based data handling
  - Managed through the `DatabaseHandler` class

---

## Project Requirements Validation
- **General**:
  - Includes 14+ classes for sufficient complexity
  - Backend-focused development with minimal UI
- **OOP Principles**:
  - Demonstrates encapsulation, inheritance, polymorphism, method overloading, and exception handling
- **SOLID**:
  - All principles are implemented for modularity and scalability
- **Design Patterns**:
  - Utilizes the Singleton pattern for database management
- **External Data Management**:
  - Integration with a SQL database for storing and retrieving data

---

## Conclusion
The present project demonstrates great capabilities regarding Java OOP and software engineering principles. The solution follows all the requirements of the descriptors: it is complex, totally respects OOP principles, implements SOLID, and a design pattern, including also external data management. Its structure and modularity make sure that this program will be able to be updated easily and extended.




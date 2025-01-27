# Library Management System

## Overview

The **Library Management System** is a Java-based application designed to streamline the management of users, books, and library-related operations. This system supports adding and managing users, books, and reports while providing administrative controls for enhanced functionality.

## Features

- **User Management:**
  - Add, list, and find users.
  - Support for admin and regular user roles.
  - Admins can block users.

- **Book Management:**
  - Add, list, and find books.
  - Categorize books by ID, title, author, and genre.

- **Reporting:**
  - Generate comprehensive reports on library usage.

- **Database Integration:**
  - Persistent data storage using MySQL for blocked users.

## Technologies Used

- **Programming Language:** Java
- **Database:** MySQL
- **Libraries:** JDBC for database connectivity

## Main Classes and Their Roles

### 1. **LibraryManagementSystem**
The entry point of the application. It provides the main menu interface and handles user input for library operations.

### 2. **User (Abstract Class)**
A base class for all user types in the system.
- **Fields:** `userId`, `name`, `email`
- **Methods:** 
  - `getUserId()`
  - `getName()`
  - `getEmail()`
  - `accessLibrary()` (abstract)

### 3. **RegularUser (Subclass of User)**
Represents a regular library user.
- **Role:** Allows browsing and borrowing books.

### 4. **AdminUser (Subclass of User)**
Represents an admin with extended privileges.
- **Role:** Manage users and block inappropriate users.

### 5. **Book**
Represents a book in the library.
- **Fields:** `bookId`, `title`, `author`, `genre`
- **Methods:** 
  - `getBookId()`
  - `getTitle()`
  - `getAuthor()`
  - `getGenre()`

### 6. **UserService**
Handles user-related operations.
- **Methods:**
  - `addUser(User user)`
  - `removeUser(String userId)`
  - `findUserById(String userId)`
  - `listAllUsers()`

### 7. **BookService**
Handles book-related operations.
- **Methods:**
  - `addBook(Book book)`
  - `removeBook(String bookId)`
  - `findBookById(String bookId)`
  - `listAllBooks()`

### 8. **ReportService**
Generates reports about library usage.
- **Methods:**
  - `generateReport()`

### 9. **DatabaseHandler**
Manages database connections and operations.
- **Methods:**
  - `getConnection()`
  - `getBlockedUsersCount()`
  - `isUserBlocked(String userId)`

### 10. **BlockedUser**
Represents a user who has been blocked from using library services.

## How to Run

1. Ensure you have Java and MySQL installed on your system.
2. Set up the database:
   - Create a database named `library`.
   - Use the following command to create the `BlockedUsers` table:
     ```sql
     CREATE TABLE BlockedUsers (
       user_id VARCHAR(255) PRIMARY KEY,
       name VARCHAR(255),
       email VARCHAR(255)
     );
     ```
3. Update database credentials in `DatabaseHandler`:
   ```java
   connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "your_username", "your_password");

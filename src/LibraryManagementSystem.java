import java.util.Scanner;

public class LibraryManagementSystem {

    public static void main(String[] args) {
        UserService userService = new UserService();
        BookService bookService = new BookService();
        ReportService reportService = new ReportService();
        BlockedUserService blockedUserService = new BlockedUserService();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your user ID: ");
        String userId = scanner.nextLine();

        // Проверяем, находится ли пользователь в списке заблокированных
        BlockedUser blockedUser = blockedUserService.findBlockedUserById(userId);
        if (blockedUser != null) {
            System.out.println("You are blocked, Exiting...");
            return;
        }

        User currentUser = userService.findUserById(userId);

        if (currentUser == null) {
            System.out.println("User not found. Exiting...");
            return;
        }

        boolean isAdmin = currentUser instanceof AdminUser;
        boolean isRegular = currentUser instanceof RegularUser;
        boolean running = true;

        while (running) {
            System.out.println("\nLibrary Management System");
            if (isAdmin) {
                System.out.println("1. Add User");
                System.out.println("2. List All Users");
                System.out.println("3. Find User by ID");
                System.out.println("4. Block User");
                System.out.println("5. Add Book");
                System.out.println("6. List All Books");
                System.out.println("7. Find Book by ID");
                System.out.println("8. Generate Report");
            } else if (isRegular) {
                System.out.println("1. Borrow Book");
            }
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (isAdmin) {
                switch (choice) {
                    case 1:
                        System.out.print("Enter user ID: ");
                        String newUserId = scanner.nextLine();
                        System.out.print("Enter user name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter user email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter user role (admin/regular): ");
                        String role = scanner.nextLine();
                        System.out.print("Enter borrowed books: ");
                        int borrowedBooks = scanner.nextInt();

                        User newUser = role.equalsIgnoreCase("admin") ?
                                new AdminUser(newUserId, name, email, userService) :
                                new RegularUser(newUserId, name, email, borrowedBooks);
                        userService.addUser(newUser);
                        System.out.println("User added successfully!");
                        break;
                    case 2:
                        userService.listAllUsers().forEach(user ->
                                System.out.println("User ID: " + user.getUserId() + ", Name: " + user.getName()));
                        break;
                    case 3:
                        System.out.print("Enter user ID to find: ");
                        User foundUser = userService.findUserById(scanner.nextLine());
                        System.out.println(foundUser != null ? "Found User: " + foundUser.getName() : "User not found.");
                        break;
                    case 4:
                        System.out.print("Enter user ID to block: ");
                        String blockUserId = scanner.nextLine();
                        ((AdminUser) currentUser).blockUser(blockUserId);
                        break;
                    case 5:
                        System.out.print("Enter book ID: ");
                        String bookId = scanner.nextLine();
                        System.out.print("Enter book title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter book author: ");
                        String author = scanner.nextLine();
                        System.out.print("Enter book genre: ");
                        String genre = scanner.nextLine();
                        System.out.print("Enter book stock: ");
                        int stock = Integer.parseInt(scanner.nextLine());

                        bookService.addBook(new Book(bookId, title, author, genre, stock));
                        System.out.println("Book added successfully!");
                        break;
                    case 6:
                        bookService.listAllBooks().forEach(b ->
                                System.out.println("Book ID: " + b.getBookId() + ", Title: " + b.getTitle()));
                        break;
                    case 7:
                        System.out.print("Enter book ID to find: ");
                        Book foundBook = bookService.findBookById(scanner.nextLine());
                        System.out.println(foundBook != null ? "Found Book: " + foundBook.getTitle() : "Book not found.");
                        break;
                    case 8:
                        reportService.generateReport();
                        break;
                    case 9:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice, try again.");
                }
            } else if (isRegular) {
                switch (choice) {
                    case 1:
                        System.out.print("Enter book ID: ");
                        String borrowBookId = scanner.nextLine();
                        DatabaseHandler dbHandler = DatabaseHandler.getInstance();
                        new LibraryManager(dbHandler).borrowBook(userId, borrowBookId);
                        break;
                    case 9:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice, try again.");
                }
            }
        }
        scanner.close();
    }
}





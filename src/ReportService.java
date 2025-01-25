import java.util.List;

public class ReportService implements IReportService {
    private DatabaseHandler dbHandler = DatabaseHandler.getInstance();

    @Override
    public void generateReport() {
        System.out.println("Generating library report...");

        List<User> users = new UserService().listAllUsers();
        System.out.println("Users in the library:");
        for (User user : users) {
            System.out.println("User ID: " + user.getUserId());
            System.out.println("Name: " + user.getName());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Access Type: " + (user instanceof AdminUser ? "Admin" : "Regular User"));
            System.out.println("Status: " + (isUserBlocked(user.getUserId()) ? "Blocked" : "Active"));
            System.out.println("----------------------------");
        }

        List<Book> books = new BookService().listAllBooks();
        System.out.println("Books in the library:");
        for (Book book : books) {
            System.out.println("Book ID: " + book.getBookId());
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("Genre: " + book.getGenre());
            System.out.println("----------------------------");
        }

        System.out.println("Total Users: " + users.size());
        System.out.println("Total Books: " + books.size());
        System.out.println("Total Blocked Users: " + getBlockedUsersCount());
    }

    private boolean isUserBlocked(String userId) {
        return dbHandler.isUserBlocked(userId);
    }

    private int getBlockedUsersCount() {
        return dbHandler.getBlockedUsersCount();
    }
}

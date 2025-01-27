import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibraryManager {

    private DatabaseHandler dbHandler;

    public LibraryManager(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public void borrowBook(String userId, String bookId) {
        try {
            // Получаем информацию о пользователе из базы данных
            String getUserQuery = "SELECT role FROM users WHERE user_id = ?";
            PreparedStatement getUserStmt = dbHandler.getConnection().prepareStatement(getUserQuery);
            getUserStmt.setString(1, userId);
            ResultSet userResult = getUserStmt.executeQuery();

            if (!userResult.next()) {
                System.out.println("User not found.");
                return;
            }

            String userType = userResult.getString("role");

            // Проверяем, заблокирован ли пользователь или является администратором
            if (userType.equals("blocked") || userType.equals("admin")) {
                System.out.println("This user is not allowed to borrow books.");
                return;
            }

            // Проверяем, есть ли книга в наличии
            String checkStockQuery = "SELECT stock FROM books WHERE book_id = ?";
            PreparedStatement checkStockStmt = dbHandler.getConnection().prepareStatement(checkStockQuery);
            checkStockStmt.setString(1, bookId);
            ResultSet stockResult = checkStockStmt.executeQuery();

            if (stockResult.next()) {
                int stock = stockResult.getInt("stock");
                if (stock <= 0) {
                    System.out.println("Book is out of stock.");
                    return;
                }
            } else {
                System.out.println("Book not found.");
                return;
            }

            // Уменьшаем stock в таблице books
            String updateStockQuery = "UPDATE books SET stock = stock - 1 WHERE book_id = ?";
            PreparedStatement updateStockStmt = dbHandler.getConnection().prepareStatement(updateStockQuery);
            updateStockStmt.setString(1, bookId);
            updateStockStmt.executeUpdate();

            // Увеличиваем borrowedBooks только для обычного пользователя
            if (userType.equals("regular")) {
                String updateBorrowedBooksQuery = "UPDATE users SET borrowedBooks = borrowedBooks + 1 WHERE user_id = ?";
                PreparedStatement updateBorrowedBooksStmt = dbHandler.getConnection().prepareStatement(updateBorrowedBooksQuery);
                updateBorrowedBooksStmt.setString(1, userId);
                updateBorrowedBooksStmt.executeUpdate();
                System.out.println("User with ID " + userId + " borrowed the book successfully.");
            }

        } catch (SQLException e) {
            System.err.println("Error while borrowing book: " + e.getMessage());
        }
    }
}

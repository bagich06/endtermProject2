import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Класс BookService реализует интерфейс IBookService для управления книгами
public class BookService implements IBookService {
    // Получаем единственный экземпляр класса DatabaseHandler для работы с базой данных
    private DatabaseHandler dbHandler = DatabaseHandler.getInstance();

    // Метод добавления книги в базу данных
    @Override
    public void addBook(Book book) {
        try {
            // SQL-запрос для добавления записи о книге в таблицу "books"
            String query = "INSERT INTO books (book_id, title, author, genre, stock) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = dbHandler.getConnection().prepareStatement(query);

            preparedStatement.setString(1, book.getBookId());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, book.getGenre());
            preparedStatement.setInt(5, book.getStock());


            // Выполняем запрос
            preparedStatement.executeUpdate();

            // Уведомляем пользователя о добавлении книги
            System.out.println("Book added: " + book.getTitle());
        } catch (SQLException e) {
            // В случае ошибки выводим информацию об исключении
            e.printStackTrace();
        }
    }

    // Метод удаления книги из базы данных по её ID
    @Override
    public void removeBook(String bookId) {
        try {
            // SQL-запрос для удаления записи из таблицы "books" по идентификатору книги
            String query = "DELETE FROM books WHERE book_id = ?";

            PreparedStatement preparedStatement = dbHandler.getConnection().prepareStatement(query);

            preparedStatement.setString(1, bookId);

            preparedStatement.executeUpdate();

            // Уведомляем пользователя об успешном удалении книги
            System.out.println("Book removed: " + bookId);
        } catch (SQLException e) {
            // В случае ошибки выводим информацию об исключении
            e.printStackTrace();
        }
    }

    // Метод поиска книги по её ID
    @Override
    public Book findBookById(String bookId) {
        Book book = null; // Объект для хранения информации о найденной книге
        try {
            String query = "SELECT * FROM books WHERE book_id = ?";

            PreparedStatement preparedStatement = dbHandler.getConnection().prepareStatement(query);

            preparedStatement.setString(1, bookId);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Если книга найдена, считываем её данные и создаём объект Book
            if (resultSet.next()) {
                String title = resultSet.getString("title"); // Название книги
                String author = resultSet.getString("author"); // Автор книги
                String genre = resultSet.getString("genre"); // Жанр книги
                int stock = resultSet.getInt("stock");

                // Создаём объект Book с полученными данными
                book = new Book(bookId, title, author, genre, stock);
            }
        } catch (SQLException e) {
            // В случае ошибки выводим информацию об исключении
            e.printStackTrace();
        }
        // Возвращаем найденную книгу или null, если ничего не найдено
        return book;
    }

    // Метод для получения списка всех книг в базе данных
    @Override
    public List<Book> listAllBooks() {
        List<Book> books = new ArrayList<>(); // Список для хранения всех книг
        try {
            // SQL-запрос для получения всех записей из таблицы "books"
            String query = "SELECT * FROM books";

            PreparedStatement preparedStatement = dbHandler.getConnection().prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Проходим по всем строкам результата
            while (resultSet.next()) {
                String bookId = resultSet.getString("book_id"); // ID книги
                String title = resultSet.getString("title"); // Название книги
                String author = resultSet.getString("author"); // Автор книги
                String genre = resultSet.getString("genre"); // Жанр книги
                int stock = resultSet.getInt("stock");

                // Создаём объект Book для каждой строки
                Book book = new Book(bookId, title, author, genre, stock);

                // Добавляем книгу в список
                books.add(book);
            }
        } catch (SQLException e) {
            // В случае ошибки выводим информацию об исключении
            e.printStackTrace();
        }
        // Возвращаем список книг
        return books;
    }
}


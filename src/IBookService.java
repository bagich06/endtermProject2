import java.util.List;

public interface IBookService {
    void addBook(Book book);
    void removeBook(String bookId);
    Book findBookById(String bookId);
    List<Book> listAllBooks();
}
public class Book {
    private String bookId;
    private String title;
    private String author;
    private String genre;
    private int stock;

    public Book(String bookId, String title, String author, String genre, int stock) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.stock = stock;
    }

    public void addBook(Book book) {
        System.out.println("Adding book: " + book.getTitle());
    }

    public void addBook(String bookId, String title, String author, String genre, int stock) {
        Book newBook = new Book(bookId, title, author, genre, stock);
        System.out.println("Adding book: " + newBook.getTitle());
    }

    public String getBookId() {
        return bookId;
    }

    public int getStock() {
        return stock;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }
}
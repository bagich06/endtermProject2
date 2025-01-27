// Класс RegularUser наследует абстрактный класс User
// Этот класс представляет обычного (не административного) пользователя библиотеки
public class RegularUser extends User {

    private int borrowedBooks;

    public int getBorrowedBooks() {
        return borrowedBooks;
    }

    // Использует ключевое слово super для вызова конструктора из базового класса User
    public RegularUser(String userId, String name, String email, int borrowedBooks) {
        super(userId, name, email); // Передаем параметры в базовый класс для инициализации
        this.borrowedBooks = borrowedBooks;
    }

    // Реализация абстрактного метода accessLibrary из класса User
    @Override
    public void accessLibrary() {
        // Описание поведения для обычного пользователя
        // Обычный пользователь может просматривать и брать книги в библиотеке
        System.out.println(name + " has regular access to browse and borrow books.");
    }
}



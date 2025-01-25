import java.util.Scanner;

public class LibraryManagementSystem {

    public static void main(String[] args) {
        // Создаем объекты для управления пользователями, книгами и отчетами
        UserService userService = new UserService();
        BookService bookService = new BookService();
        ReportService reportService = new ReportService();

        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        // Основной цикл программы, который работает, пока не выберем выход
        while (running) {
            // Отображаем меню для пользователя
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add User");
            System.out.println("2. List All Users");
            System.out.println("3. Find User by ID");
            System.out.println("4. Block User");
            System.out.println("5. Add Book");
            System.out.println("6. List All Books");
            System.out.println("7. Find Book by ID");
            System.out.println("8. Generate Report");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            // Получаем выбор пользователя
            int choice = scanner.nextInt();
            scanner.nextLine();  // Чистим буфер после ввода числа

            // Используем конструкцию switch для обработки выбора пользователя
            switch (choice) {
                case 1:  // Добавление нового пользователя
                    System.out.print("Enter user ID: ");
                    String userId = scanner.nextLine();  // Вводим ID пользователя
                    System.out.print("Enter user name: ");
                    String name = scanner.nextLine();  // Вводим имя пользователя
                    System.out.print("Enter user email: ");
                    String email = scanner.nextLine();  // Вводим email
                    System.out.print("Enter user role (admin/regular): ");
                    String role = scanner.nextLine();  // Вводим роль (администратор или обычный пользователь)

                    // Проверяем роль пользователя, если администратор — создаем админа
                    if (role.equalsIgnoreCase("admin")) {
                        User admin = new AdminUser(userId, name, email, userService);  // Создаем администратора
                        userService.addUser(admin);  // Добавляем его в систему
                    } else {  // В противном случае — обычного пользователя
                        User regularUser = new RegularUser(userId, name, email);
                        userService.addUser(regularUser);  // Добавляем обычного пользователя
                    }
                    System.out.println("User added successfully!");  // Подтверждаем успешное добавление
                    break;

                case 2:  // Показать всех пользователей
                    System.out.println("Listing all users:");
                    // Перебираем всех пользователей и выводим информацию
                    for (User user : userService.listAllUsers()) {
                        System.out.println("User ID: " + user.getUserId() + ", Name: " + user.getName() + ", Email: " + user.getEmail());
                    }
                    break;

                case 3:  // Поиск пользователя по ID
                    System.out.print("Enter user ID to find: ");
                    String searchUserId = scanner.nextLine();  // Вводим ID пользователя для поиска
                    User foundUser = userService.findUserById(searchUserId);  // Ищем пользователя
                    if (foundUser != null) {
                        System.out.println("Found User: " + foundUser.getName());  // Если нашли — выводим его имя
                    } else {
                        System.out.println("User not found.");  // Если не нашли — выводим сообщение
                    }
                    break;

                case 4:  // Блокировка пользователя (доступ только для администратора)
                    System.out.print("Enter admin ID to verify: ");
                    String adminId = scanner.nextLine();  // Вводим ID администратора для проверки

                    // Ищем администратора по введенному ID
                    User adminUser = userService.findUserById(adminId);
                    if (adminUser instanceof AdminUser) {  // Проверяем, является ли пользователь администратором
                        System.out.print("Enter user ID to block: ");
                        String blockUserId = scanner.nextLine();  // Вводим ID пользователя для блокировки

                        // Ищем пользователя, которого нужно заблокировать
                        User userToBlock = userService.findUserById(blockUserId);
                        if (userToBlock != null) {
                            ((AdminUser) adminUser).blockUser(blockUserId);  // Блокируем пользователя
                        } else {
                            System.out.println("User with ID " + blockUserId + " not found.");  // Если не нашли пользователя — выводим сообщение
                        }
                    } else {
                        System.out.println("Only admins can block users.");  // Если это не администратор — выводим сообщение
                    }
                    break;

                case 5:  // Добавление новой книги
                    System.out.print("Enter book ID: ");
                    String bookId = scanner.nextLine();  // Вводим ID книги
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();  // Вводим название книги
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();  // Вводим автора
                    System.out.print("Enter book genre: ");
                    String genre = scanner.nextLine();  // Вводим жанр

                    // Создаем объект книги и добавляем его в систему
                    Book book = new Book(bookId, title, author, genre);
                    bookService.addBook(book);
                    System.out.println("Book added successfully!");  // Подтверждаем успешное добавление
                    break;

                case 6:  // Показать все книги
                    System.out.println("Listing all books:");
                    // Перебираем все книги и выводим их информацию
                    for (Book b : bookService.listAllBooks()) {
                        System.out.println("Book ID: " + b.getBookId() + ", Title: " + b.getTitle() + ", Author: " + b.getAuthor() + ", Genre: " + b.getGenre());
                    }
                    break;

                case 7:  // Поиск книги по ID
                    System.out.print("Enter book ID to find: ");
                    String searchBookId = scanner.nextLine();  // Вводим ID книги для поиска
                    Book foundBook = bookService.findBookById(searchBookId);  // Ищем книгу
                    if (foundBook != null) {
                        System.out.println("Found Book: " + foundBook.getTitle());  // Если нашли — выводим название книги
                    } else {
                        System.out.println("Book not found.");  // Если не нашли — выводим сообщение
                    }
                    break;

                case 8:  // Генерация отчета
                    reportService.generateReport();  // Генерируем отчет
                    break;

                case 9:  // Выход из системы
                    System.out.println("Exiting the system.");
                    running = false;  // Закрываем программу, установив флаг в false
                    break;

                default:
                    System.out.println("Invalid choice, please try again.");  // Если введен неправильный выбор — выводим сообщение
                    break;
            }
        }

        scanner.close();
    }
}


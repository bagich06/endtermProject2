import java.sql.PreparedStatement;
import java.sql.SQLException;

// Класс AdminUser наследует базовый класс User
// Этот класс представляет администратора библиотеки с расширенными правами
public class AdminUser extends User {

    // Создаем объект dbHandler для работы с базой данных
    // Используем Singleton-класс DatabaseHandler для управления соединением с базой данных
    private DatabaseHandler dbHandler = DatabaseHandler.getInstance();

    private UserService userService;

    public AdminUser(String userId, String name, String email, UserService userService) {
        super(userId, name, email); // Инициализируем свойства из базового класса User
        this.userService = userService;
    }

    // Реализация абстрактного метода accessLibrary из класса User
    @Override
    public void accessLibrary() {
        // Поведение администратора при доступе к библиотеке
        System.out.println(name + " has full access to manage the library.");
    }

    // Метод для блокировки пользователя
    public void blockUser(String userId) {
        // Используем userService, чтобы найти пользователя по его ID
        User userToBlock = userService.findUserById(userId);

        // Если пользователь найден
        if (userToBlock != null) {
            // Добавляем пользователя в таблицу заблокированных пользователей
            addBlockedUserToDatabase(userToBlock);

            // Удаляем пользователя из таблицы users
            deleteUserFromDatabase(userToBlock);

            System.out.println("User with ID: " + userId + " has been blocked and removed from the users table.");
        } else {
            // Если пользователь с указанным ID не найден
            System.out.println("User with ID " + userId + " not found.");
        }
    }

    // Приватный метод для добавления пользователя в таблицу заблокированных пользователей в базе данных
    private void addBlockedUserToDatabase(User user) {
        // SQL-запрос для добавления записи в таблицу BlockedUsers
        String query = "INSERT INTO BlockedUsers (user_id, name, email) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = dbHandler.getConnection().prepareStatement(query)) {
            // Устанавливаем значения параметров запроса
            preparedStatement.setString(1, user.getUserId()); // Устанавливаем user_id
            preparedStatement.setString(2, user.getName());   // Устанавливаем name
            preparedStatement.setString(3, user.getEmail());  // Устанавливаем email

            // Выполняем SQL-запрос
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Обрабатываем возможные ошибки при выполнении запроса
            System.out.println("Error adding blocked user to the database: " + e.getMessage());
        }
    }

    // Приватный метод для удаления пользователя из таблицы users
    private void deleteUserFromDatabase(User user) {
        // SQL-запрос для удаления пользователя
        String query = "DELETE FROM users WHERE user_id = ?";

        try (PreparedStatement preparedStatement = dbHandler.getConnection().prepareStatement(query)) {
            // Устанавливаем значение параметра запроса
            preparedStatement.setString(1, user.getUserId());

            // Выполняем SQL-запрос
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Обрабатываем возможные ошибки при выполнении запроса
            System.out.println("Error deleting user from database: " + e.getMessage());
        }
    }
}






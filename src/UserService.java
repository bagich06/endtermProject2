import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Класс UserService реализует интерфейс IUserService
public class UserService implements IUserService {
    // Экземпляр класса DatabaseHandler, отвечающего за подключение к базе данных
    private DatabaseHandler dbHandler = DatabaseHandler.getInstance();

    // Метод для добавления нового пользователя в базу данных
    public void addUser(User user) {
        try {
            // SQL-запрос для вставки новой записи в таблицу users
            String query = "INSERT INTO users (user_id, name, email, role, borrowedBooks) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = dbHandler.getConnection().prepareStatement(query);
            // Устанавливаем значения параметров в запросе
            preparedStatement.setString(1, user.getUserId()); // ID пользователя
            preparedStatement.setString(2, user.getName()); // Имя пользователя
            preparedStatement.setString(3, user.getEmail()); // Электронная почта
            // Определяем роль пользователя: "admin" для AdminUser или "regular" для обычного пользователя
            preparedStatement.setString(4, (user instanceof AdminUser) ? "admin" : "regular");
            if (user instanceof RegularUser) {
                int borrowedBooks = ((RegularUser) user).getBorrowedBooks();
                preparedStatement.setInt(5, borrowedBooks);
            } else {
                // Если это администратор, значение borrowedBooks будет 0 или NULL
                preparedStatement.setNull(5, java.sql.Types.INTEGER);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUser(String userId) {
    }

    // Метод для получения списка всех пользователей из базы данных
    public List<User> listAllUsers() {
        // Создаём список, в который будем добавлять пользователей
        List<User> users = new ArrayList<>();
        try {
            // SQL-запрос для выбора всех записей из таблицы users
            String query = "SELECT * FROM users";
            PreparedStatement preparedStatement = dbHandler.getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Обрабатываем каждую строку результата
            while (resultSet.next()) {
                String userId = resultSet.getString("user_id"); // Получаем ID пользователя
                String name = resultSet.getString("name"); // Получаем имя пользователя
                String email = resultSet.getString("email"); // Получаем email пользователя
                String role = resultSet.getString("role"); // Получаем роль пользователя
                int borrowedBooks = resultSet.getInt("borrowedBooks");

                // Создаём экземпляр UserService для передачи в AdminUser
                UserService userService = new UserService();

                // В зависимости от роли создаём соответствующий объект
                User user;
                if (role.equals("admin")) {
                    // Если роль "admin", создаём объект AdminUser
                    user = new AdminUser(userId, name, email, userService);
                } else {
                    // Иначе создаём объект RegularUser
                    user = new RegularUser(userId, name, email, borrowedBooks);
                }

                // Добавляем пользователя в список
                users.add(user);
            }
        } catch (SQLException e) {
            // Если возникает ошибка при работе с базой данных, выводим её в консоль
            e.printStackTrace();
        }
        // Возвращаем список всех пользователей
        return users;
    }

    // Метод для поиска пользователя по его ID
    public User findUserById(String userId) {
        // Переменная для хранения найденного пользователя
        User user = null;
        try {
            // SQL-запрос для поиска пользователя по ID
            String query = "SELECT * FROM users WHERE user_id = ?";
            PreparedStatement preparedStatement = dbHandler.getConnection().prepareStatement(query);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Если пользователь найден
            if (resultSet.next()) {
                String name = resultSet.getString("name"); // Получаем имя пользователя
                String email = resultSet.getString("email"); // Получаем email
                String role = resultSet.getString("role"); // Получаем роль
                int borrowedBooks = resultSet.getInt("borrowedBooks");

                // Создаём экземпляр UserService для передачи в AdminUser
                UserService userService = new UserService();

                // В зависимости от роли создаём соответствующий объект
                if (role.equals("admin")) {
                    // Если роль "admin", создаём объект AdminUser
                    user = new AdminUser(userId, name, email, userService);
                } else {
                    // Иначе создаём объект RegularUser
                    user = new RegularUser(userId, name, email, borrowedBooks);
                }
            }
        } catch (SQLException e) {
            // Если возникает ошибка при работе с базой данных, выводим её в консоль
            e.printStackTrace();
        }
        // Возвращаем найденного пользователя (или null, если пользователь не найден)
        return user;
    }
}


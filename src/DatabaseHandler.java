import java.sql.*;

// Этот класс использует паттерн Singleton для работы с базой данных
public class DatabaseHandler {
    // Единственный экземпляр класса DatabaseHandler
    private static DatabaseHandler instance;

    // Поле для хранения подключения к базе данных
    private Connection connection;

    // Приватный конструктор (чтобы нельзя было создать объект напрямую)
    private DatabaseHandler() {
        try {
            // Устанавливаем соединение с базой данных
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "12012006");
        } catch (SQLException e) {
            // Если возникает ошибка подключения, выводим её стек
            e.printStackTrace();
        }
    }

    // Метод для получения единственного экземпляра класса DatabaseHandler
    public static DatabaseHandler getInstance() {
        // Если объект ещё не создан, создаём его
        if (instance == null) {
            instance = new DatabaseHandler();
        }
        // Возвращаем экземпляр
        return instance;
    }

    // Метод для получения текущего соединения с базой данных
    public Connection getConnection() {
        return connection;
    }

    // Метод для получения количества заблокированных пользователей
    public int getBlockedUsersCount() {
        int count = 0; // Переменная для хранения количества заблокированных пользователей

        // SQL-запрос для подсчёта всех записей в таблице BlockedUsers
        String query = "SELECT COUNT(*) FROM BlockedUsers";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            // Если есть результаты, получаем значение из первой строки
            if (resultSet.next()) {
                count = resultSet.getInt(1); // Берём первое значение (количество)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Возвращаем количество заблокированных пользователей
        return count;
    }

    // Метод для проверки, заблокирован ли пользователь
    public boolean isUserBlocked(String userId) {
        boolean isBlocked = false; // Переменная для хранения результата

        // SQL-запрос для проверки, есть ли пользователь в таблице BlockedUsers
        String query = "SELECT COUNT(*) FROM BlockedUsers WHERE user_id = ?";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            // Устанавливаем значение для параметра user_id
            preparedStatement.setString(1, userId);

            // Выполняем запрос
            ResultSet resultSet = preparedStatement.executeQuery();

            // Если есть результаты, проверяем значение в первой строке
            if (resultSet.next()) {
                isBlocked = resultSet.getInt(1) > 0; // Если результат больше 0, пользователь заблокирован
            }
        } catch (SQLException e) {
            // В случае ошибки выводим её стек
            e.printStackTrace();
        }

        // Возвращаем результат проверки
        return isBlocked;
    }
}


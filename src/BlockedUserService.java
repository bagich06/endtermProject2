import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Сервис для управления заблокированными пользователями
public class BlockedUserService {
    // Экземпляр класса DatabaseHandler для работы с базой данных
    private DatabaseHandler dbHandler = DatabaseHandler.getInstance();

    // Метод для добавления заблокированного пользователя в базу данных
    public void addBlockedUser(BlockedUser user) {
        try {
            String query = "INSERT INTO BlockedUsers (user_id, name, email) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = dbHandler.getConnection().prepareStatement(query);
            preparedStatement.setString(1, user.getUserId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для удаления заблокированного пользователя из базы данных
    public void removeBlockedUser(String userId) {
        try {
            String query = "DELETE FROM BlockedUsers WHERE user_id = ?";
            PreparedStatement preparedStatement = dbHandler.getConnection().prepareStatement(query);
            preparedStatement.setString(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для получения списка всех заблокированных пользователей
    public List<BlockedUser> listAllBlockedUsers() {
        List<BlockedUser> blockedUsers = new ArrayList<>();
        try {
            String query = "SELECT * FROM BlockedUsers";
            PreparedStatement preparedStatement = dbHandler.getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");

                BlockedUser blockedUser = new BlockedUser(userId, name, email);
                blockedUsers.add(blockedUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blockedUsers;
    }

    // Метод для поиска заблокированного пользователя по его ID
    public BlockedUser findBlockedUserById(String userId) {
        BlockedUser blockedUser = null;
        try {
            String query = "SELECT * FROM BlockedUsers WHERE user_id = ?";
            PreparedStatement preparedStatement = dbHandler.getConnection().prepareStatement(query);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");

                blockedUser = new BlockedUser(userId, name, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blockedUser;
    }
}


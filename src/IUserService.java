import java.util.List;

public interface IUserService {
    void addUser(User user);
    void removeUser(String userId);
    User findUserById(String userId);
    List<User> listAllUsers();
}
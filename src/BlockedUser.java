public class BlockedUser extends User {
    private String userId;
    private String name;
    private String email;

    public BlockedUser(String userId, String name, String email) {
        super(userId, name, email);
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public void accessLibrary() {

    }
}

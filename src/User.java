// Абстрактный класс User определяет общие свойства и методы для всех типов пользователей
public abstract class User {
    // Они объявлены с модификатором protected, чтобы дочерние классы могли их использовать напрямую.
    protected String userId;
    protected String name;
    protected String email;

    // Конструктор для инициализации основных данных пользователя
    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
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

    // Абстрактный метод, который должен быть реализован в дочерних классах
    // Этот метод описывает, как пользователь будет взаимодействовать с библиотекой
    public abstract void accessLibrary();
}


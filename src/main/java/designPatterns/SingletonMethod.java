package designPatterns;

class DatabaseConnection{
    private static DatabaseConnection instance;

    private DatabaseConnection(){
        // it will stop initialization of DatabaseConnection
    }

    public static DatabaseConnection getInstance(){
        if(instance == null){
            instance = new DatabaseConnection();
            return instance;
        }
        return instance;
    }

    public void query(String query){
        // sql query
    }
}

public class SingletonMethod {
    public static void main(String[] args) {
        DatabaseConnection dbInstance = DatabaseConnection.getInstance();
        dbInstance.query("SELECT * FROM students");
    }
}

/*
Singleton disadvantages

1. Global state
    * The same object is accessible from anywhere.
    * Any part of the application can change its state.
    * Makes data flow harder to track.

2. Hides dependencies
    * A class may internally call:
    ConfigManager.getInstance();
    So from the constructor, you can’t see that ConfigManager is actually required.
    * Dependency Injection makes this explicit:
    UserService(ConfigManager config)

3. Harder to unit test
    * Because the class directly gets the Singleton, it’s difficult to replace it with a mock/fake.
    * With dependency injection, you can easily provide a test implementation.
 */
package worklist.entity;

/**
 * Class represents the users of the app (employees of a company).
 */

public class Employee {

    private int id;
    private String username;
    private String password;

    public Employee(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Employee(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

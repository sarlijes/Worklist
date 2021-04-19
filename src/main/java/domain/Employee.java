package domain;

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

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao implements Dao<Employee, Integer> {

    private Connection connection;

    public EmployeeDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Employee create(Employee object) throws SQLException {
        return null;
    }

    @Override
    public Employee read(Integer id) throws SQLException {
        return null;
    }

    @Override
    public Employee update(Employee object, Integer id) throws SQLException {
        return null;
    }

    @Override
    public void delete(Integer id) throws SQLException {

    }

    @Override
    public List<Employee> list() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Employee;");
        ResultSet resultSet = statement.executeQuery();
        List<Employee> employees = new ArrayList<>();

        while (resultSet.next()) {
            employees.add(parseEmployeeFromResult(resultSet));
        }
        return employees;
    }

    public boolean authenticate(String username, String password) throws SQLException  {
        PreparedStatement statement =
                connection.prepareStatement("SELECT * FROM Employee where username = ? and password = ?;");
        statement.setString(1, username);
        statement.setString(2, password);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return true;
        } else {
            return false;
        }
    }

    public Employee parseEmployeeFromResult(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String username = resultSet.getString("name");
        Employee e = new Employee(id, username);
        return e;
    }

}

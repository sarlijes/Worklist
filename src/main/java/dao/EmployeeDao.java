package dao;

import domain.Employee;
import domain.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao implements Dao<Employee, Integer> {

    private Connection connection;
    private SQLUtils sqlUtils;

    public EmployeeDao(Connection connection) {
        this.connection = connection;
        this.sqlUtils = new SQLUtils();
    }

    @Override
    public Employee create(Employee employee) throws SQLException {

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Employee (username, password) " +
                "VALUES (?, ?)");
        stmt.setString(1, employee.getUsername());
        stmt.setString(2, employee.getPassword());
        stmt.executeUpdate();

        return read(sqlUtils.getGeneratedId(stmt));
    }

    @Override
    public Employee read(Integer id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Employee WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            return null;
        }

        Employee e = parseEmployeeFromResult(rs);

        stmt.close();
        rs.close();

        return e;
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
        String username = resultSet.getString("username");
        Employee e = new Employee(id, username);
        return e;
    }

}

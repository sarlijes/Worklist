package worklist.dao;

import worklist.entity.Employee;

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

    /**
     * Adds a new <code>Employee</code> into the database, given that the username is unique
     *
     * @param   employee          <code>Employee</code> created with the info provided by an user
     *
     * @return the newly created employee or null, if the username is already in use
     *
     * @throws  SQLException      Indicates that an <code>SQLException</code> has occurred during transaction
     */

    @Override
    public Employee create(Employee employee) throws SQLException {

        if (usernameExists(employee.getUsername())) {
            return null;
        }

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Employee (username, password) " +
                "VALUES (?, ?)");
        stmt.setString(1, employee.getUsername());
        stmt.setString(2, employee.getPassword());
        stmt.executeUpdate();

        return read(sqlUtils.getGeneratedId(stmt));
    }

    /**
     * Checks whether an employee with the given username already exists to avoid duplicates.
     *
     * @param   username        username given by an user when creating an account
     *
     * @return whether the username is in use or not
     * @throws  SQLException    Indicates that an <code>SQLException</code> has occurred during transaction
     */

    private boolean usernameExists(String username) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Employee WHERE username = ?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        return rs.next();
    }

    /**
     *
     * Reads an <code>Employee</code> from the database by their id and returns that <code>Employee</code>
     *
     * @param   id              employee's id
     *
     * @return the <code>Employee</code> found with that id, or null if no employee is found
     * @throws SQLException     Indicates that an <code>SQLException</code> has occurred during transaction
     */

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

    /**
     *
     * !! Not yet implemented, not used (not in the scope of this course project)
     *
     * @param   employee            an employee object with the new info
     * @param   id                  the id of the employee to edit
     *
     * @return  the given employee without editing it
     * @throws SQLException          Indicates that an <code>SQLException</code> has occurred during transaction
     */

    @Override
    public Employee update(Employee employee, Integer id) throws SQLException {
        // Not yet implemented, not used
        return employee;
    }

    /**
     *
     * !! Not yet implemented, not used (not in the scope of this course project)
     *
     * @param   id                    the id of the employee to delete
     * @throws  SQLException          Indicates that an <code>SQLException</code> has occurred during transaction
     */

    @Override
    public void delete(Integer id) throws SQLException {
    }

    /**
     * Lists all the <code>Employee</code> objects from the database
     *
     * @return all the <code>Employee</code> objects as an arraylist
     * @throws SQLException              Indicates that an <code>SQLException</code> has occurred during transaction
     */

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

    /**
     * Authenticates the user by their username and password
     *
     * @param username                  The username given by an user trying to log in
     * @param password                  The password given by an user trying to log in
     * @return an <code>Employee</code>, if authentication was successful, and null in case it wasn't
     * @throws SQLException              Indicates that an <code>SQLException</code> has occurred during transaction
     */

    public Employee authenticate(String username, String password) throws SQLException  {
        PreparedStatement statement =
                connection.prepareStatement("SELECT * FROM Employee where username = ? and password = ?;");
        statement.setString(1, username);
        statement.setString(2, password);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Employee e = parseEmployeeFromResult(resultSet);
            statement.close();
            resultSet.close();
            return e;
        } else {
            return null;
        }
    }

    /**
     * Parses an <code>Employee</code> from a <code>ResultSet</code>
     *
     * @param resultSet              <code>ResultSet</code> returned from the database
     * @return                       <code>Employee</code> object
     * @throws SQLException          Indicates that an <code>SQLException</code> has occurred during transaction
     */
    public Employee parseEmployeeFromResult(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String username = resultSet.getString("username");
        Employee e = new Employee(id, username);
        return e;
    }

}

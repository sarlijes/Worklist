import static org.junit.Assert.*;

import dao.EmployeeDao;
import dao.SQLUtils;
import domain.Employee;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeTest {

    private static EmployeeDao employeeDao;
    private static Connection connection;
    private static SQLUtils sqlUtils;

    @BeforeClass
    public static void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:");
        employeeDao = new EmployeeDao(connection);
        sqlUtils = new SQLUtils();
        sqlUtils.createTables(connection);

        PreparedStatement stmt = connection.prepareStatement(
                "insert into employee (username, password) " +
                        "values ('user2828', 'superSecretPassword1');" +
                        "insert into employee (username, password) " +
                        "values ('user113', 'superSecretPassword2');" +
                        "insert into employee (username, password) " +
                        "values ('user244', 'superSecretPassword3');" +
                        "insert into employee (username, password) " +
                        "values ('user678', 'superSecretPassword4');" +
                        "");
        stmt.executeUpdate();
        stmt.close();

        assertEquals(4, employeeDao.list().size());
    }

    @Test
    public void canNotReadNonexistingEmployee() throws SQLException {
        Employee e = employeeDao.read(985);
        assert (e == null);
    }

    @Test
    public void employeeConstructorWorks() {
        Employee e = new Employee("user123", "Kd6od985d??");

        assert (e != null);
        assertEquals("user123", e.getUsername());
        assertEquals("Kd6od985d??", e.getPassword());
    }

    @Test
    public void cannotCreateTwoUsersWithSameUsername() throws SQLException {
        Employee e = new Employee("diana", "12345");
        e = employeeDao.create(e);
        assert (e != null);

        Employee another = new Employee("diana", "88888");
        another = employeeDao.create(another);
        assert (another == null);
    }

    @Test
    public void createdEmployeeHasTheUsernameItWasCreatedWith() throws SQLException {
        Employee e = new Employee("svante85", "12345");
        assert (e != null);
        e = employeeDao.create(e);
        assertEquals("svante85", e.getUsername());
    }

    @Test
    public void passwordIsNotReturnedWhenCreatingEmployee() throws SQLException {
        Employee e = new Employee("pekka", "akkep");
        e = employeeDao.create(e);
        assertEquals("pekka", e.getUsername());
        assertEquals(null, e.getPassword());
    }

    @Test
    public void canAuthenticateEmployeeWithCorrectCredentials() throws SQLException {
        Employee e = new Employee("daemu", "secret");
        e = employeeDao.create(e);

        assert(employeeDao.authenticate("daemu", "secret") != null);
    }

    @Test
    public void cannotAuthenticateEmployeeWithIncorrectCredentials() throws SQLException {
        Employee e = new Employee("pentti", "111666");
        e = employeeDao.create(e);

        assert(employeeDao.authenticate("pertti", "111666") == null);
        assert(employeeDao.authenticate("pentti", "11166") == null);
    }


}


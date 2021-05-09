package worklist;

import org.junit.BeforeClass;
import org.junit.Test;
import worklist.dao.EmployeeDao;
import worklist.dao.JobDao;
import worklist.dao.MaterialDao;
import worklist.dao.SQLUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;



public class SQLUtilsTest {

    private static Connection connection;
    private static SQLUtils sqlUtils;

    @Test
    public void testGeneratedIds() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:");

        sqlUtils = new SQLUtils();
        sqlUtils.createTables(connection);

        PreparedStatement generatedIdTestStatement = connection.prepareStatement("" +
                "insert into job (name, created, duedate, quantity, material, workloadestimate, details, customer) " +
                "values ('Test data 1', '2021-02-23T00:00:00.0', '2021-04-04T00:00:00.0', 5.0, 'Test data material X', " +
                "4.0, 'details', 'Customer X');");

        assertEquals(-1, sqlUtils.getGeneratedId(generatedIdTestStatement));
        generatedIdTestStatement.executeUpdate();
        assertEquals(1, sqlUtils.getGeneratedId(generatedIdTestStatement));
        generatedIdTestStatement.close();

        PreparedStatement stmt = connection.prepareStatement("" +
                "insert into job (name, created, duedate, quantity, material, workloadestimate, details, customer) " +
                "values ('Test data 2', now(), '2021-02-02T00:00:00.0', 2.0, 'CU-OF', 1.0, 'Test data material Y', " +
                "'internal');");

        stmt.executeUpdate();
        assertEquals(2, sqlUtils.getGeneratedId(stmt));
        stmt.close();

    }
}
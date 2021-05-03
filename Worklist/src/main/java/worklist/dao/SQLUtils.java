/**
 * Utility class to provide useful methods to allow access to database. Used for both persistant H2 database and
 * the in-memomry database used in testing.
 */

package worklist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUtils {

    /**
     * Returns the id of a generated database entry
     *
     * @param statement         <code>PreparedStatement</code> that has been just run
     * @return                  the id of the database entry that was created by running the statement
     * @throws SQLException     Indicates that an <code>SQLException</code> has occurred during transaction
     */

    public int getGeneratedId(PreparedStatement statement) throws SQLException {
        int id = -1;
        ResultSet generatedKeys = statement.getGeneratedKeys();

        if (generatedKeys.next()) {
            id = generatedKeys.getInt(1);
        }
        generatedKeys.close();
        statement.close();
        return id;
    }

    /**
     * Method that calls various other methods to create database tables
     *
     * @param   connection      The <code>Connection</code> used to create the tables
     * @throws  SQLException    Indicates that an <code>SQLException</code> has occurred during transaction
     */

    public void createTables(Connection connection) throws SQLException {
        createJobTable(connection);
        createEmployeeTable(connection);
        createMaterialTable(connection);
        addDefaultMaterials(connection);
        addConstraints(connection);
    }

    /**
     * Creates some default Materials to database
     *
     * @param   connection      The <code>Connection</code> used to create the tables
     * @throws  SQLException    Indicates that an <code>SQLException</code> has occurred during transaction
     */

    private void addDefaultMaterials(Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "insert into material (name, details) " +
                        "values ('Alumiini 7075T6', '');" +
                        "insert into material (name, details) " +
                        "values ('Alumiiniputki 30mm', 'Hyllytavaran pituus 6 m')");
        stmt.executeUpdate();
        stmt.close();
    }

    /**
     * Creates a table to store <code>Job</code> entries in database, if it doesn't exist already
     *
     * @param   connection      The <code>Connection</code> used to create the table
     * @throws SQLException     Indicates that an <code>SQLException</code> has occurred during transaction
     */

    private void createJobTable(Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("" +
                "CREATE TABLE if not exists Job (id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "created DATETIME," +
                "finished DATETIME," +
                "deleted DATETIME," +
                "duedate DATETIME," +
                "name VARCHAR(1024)," +
                "quantity INTEGER," +
                "material VARCHAR(1024)," +
                "workloadestimate FLOAT," +
                "workloadactual FLOAT," +
                "details VARCHAR(2048)," +
                "customer VARCHAR(1024)," +
                "creator_id INTEGER," +
                "material_id INTEGER" +
                ");");
        stmt.executeUpdate();
        stmt.close();
    }

    /**
     * Creates a table to store <code>Employee</code> entries in database, if it doesn't exist already
     *
     * @param   connection      The <code>Connection</code> used to create the table
     * @throws SQLException     Indicates that an <code>SQLException</code> has occurred during transaction
     */

    private void createEmployeeTable(Connection connection) throws SQLException {

        PreparedStatement stmt = connection.prepareStatement("" +
                "CREATE TABLE if not exists Employee (id INT PRIMARY KEY AUTO_INCREMENT, " +
                "username VARCHAR(32)," +
                "password VARCHAR(32));");

        stmt.executeUpdate();
        stmt.close();

    }

    /**
     * Creates a table to store <code>Material</code> entries in database, if it doesn't exist already
     *
     * @param   connection      The <code>Connection</code> used to create the table
     * @throws SQLException     Indicates that an <code>SQLException</code> has occurred during transaction
     */

    private void createMaterialTable(Connection connection) throws SQLException {

        PreparedStatement stmt = connection.prepareStatement("" +
                "CREATE TABLE if not exists Material (id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(32)," +
                "details VARCHAR(1024));");

        stmt.executeUpdate();
        stmt.close();

    }

    /**
     * Adds constraints to the database (links between tables and restrictions such as "employee username must be
     * unique")
     *
     * @param   connection      The <code>Connection</code> used to create the constraints
     * @throws SQLException     Indicates that an <code>SQLException</code> has occurred during transaction
     */

    private void addConstraints(Connection connection) throws SQLException {

        PreparedStatement stmt = connection.prepareStatement(
                "ALTER TABLE Job ADD FOREIGN KEY (creator_id) REFERENCES Employee(id);" +
                        "ALTER TABLE Job ADD FOREIGN KEY (material_id) REFERENCES Material(id);" +
                        "ALTER TABLE Employee ADD CONSTRAINT if not exists employee_unique_username UNIQUE(username);");

        // TODO add constraints to username and password length
        // TODO save password as encrypted

        stmt.executeUpdate();
        stmt.close();

    }
}

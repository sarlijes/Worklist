package worklist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUtils {

    public int getGeneratedId(PreparedStatement stmt) throws SQLException {
        int id = -1;
        ResultSet generatedKeys = stmt.getGeneratedKeys();

        if (generatedKeys.next()) {
            id = generatedKeys.getInt(1);
        }
        generatedKeys.close();
        stmt.close();
        return id;
    }

    public void createTables(Connection connection) throws SQLException {

        createJobTable(connection);
        createEmployeeTable(connection);
        createMaterialTable(connection);
        addConstraints(connection);
    }

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

    private void createEmployeeTable(Connection connection) throws SQLException {

        PreparedStatement stmt = connection.prepareStatement("" +
                "CREATE TABLE if not exists Employee (id INT PRIMARY KEY AUTO_INCREMENT, " +
                "username VARCHAR(32)," +
                "password VARCHAR(32));");

        stmt.executeUpdate();
        stmt.close();

    }

    private void createMaterialTable(Connection connection) throws SQLException {

        PreparedStatement stmt = connection.prepareStatement("" +
                "CREATE TABLE if not exists Material (id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(32)," +
                "details VARCHAR(1024));");

        stmt.executeUpdate();
        stmt.close();

    }

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

package dao;

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
        PreparedStatement createJobStatement = connection.prepareStatement("" +
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
                "customer VARCHAR(1024)" +
                ");");
        createJobStatement.executeUpdate();
        createJobStatement.close();

        PreparedStatement createEmployeeStatement = connection.prepareStatement("" +
                "CREATE TABLE if not exists Employee (id INT PRIMARY KEY AUTO_INCREMENT, " +
                "username VARCHAR(32)," +
                "password VARCHAR(32)" +
                ");");
        createEmployeeStatement.executeUpdate();
        createEmployeeStatement.close();
    }
}

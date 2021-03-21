package dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JobDao implements Dao<Job, Integer>  {

    @Override
    public void create(Job object) throws SQLException {
        // TODO
    }

    @Override
    public Job read(Integer key) throws SQLException {
        // TODO
        return null;
    }

    @Override
    public Job update(Job object) throws SQLException {
        // TODO
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // TODO
    }

    @Override
    public List<Job> list() throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:h2:./db", "sa", "");
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Job");
        ResultSet resultSet = statement.executeQuery();
        List<Job> jobs = new ArrayList<>();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            LocalDateTime created = LocalDateTime.now();;
            Date dueDate = resultSet.getDate("dueDate");
            Date finished = resultSet.getDate("finished");
            Date deleted = resultSet.getDate("deleted");
            int quantity = resultSet.getInt("quantity");
            String material = resultSet.getString("material");
            Double workloadEstimate =  resultSet.getDouble("workloadEstimate");
            Double workloadActual =  resultSet.getDouble("workloadActual");
            String details = resultSet.getString("details");
            String customer = resultSet.getString("customer");

            Job j = new Job(id, name, created, dueDate, finished, deleted, quantity, material,
                    workloadEstimate, workloadActual, details, customer);
            jobs.add(j);
        }
        connection.close();
        return jobs;
    }

}

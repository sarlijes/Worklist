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

    private Job parseJobFromResult(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");

        LocalDateTime created = resultSet.getTimestamp("created") != null ?
                resultSet.getTimestamp("created").toLocalDateTime() : null;
        LocalDate dueDate = resultSet.getTimestamp("dueDate") != null ?
                resultSet.getTimestamp("dueDate").toLocalDateTime().toLocalDate() : null;
        LocalDate finished = resultSet.getTimestamp("finished") != null ?
                resultSet.getTimestamp("finished").toLocalDateTime().toLocalDate() : null;
        LocalDate deleted = resultSet.getTimestamp("deleted") != null ?
                resultSet.getTimestamp("deleted").toLocalDateTime().toLocalDate() : null;

        int quantity = resultSet.getInt("quantity");
        String material = resultSet.getString("material");
        Double workloadEstimate =  resultSet.getDouble("workloadEstimate");
        Double workloadActual =  resultSet.getDouble("workloadActual");
        String details = resultSet.getString("details");
        String customer = resultSet.getString("customer");

        Job j = new Job(id, name, created, dueDate, finished, deleted, quantity, material,
                workloadEstimate, workloadActual, details, customer);

        return j;

    }

    @Override
    public List<Job> list() throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:h2:./db", "sa", "");
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Job");
        ResultSet resultSet = statement.executeQuery();
        List<Job> jobs = new ArrayList<>();

        while (resultSet.next()) {
            jobs.add(parseJobFromResult(resultSet));
        }

        connection.close();
        return jobs;
    }

}

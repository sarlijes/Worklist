package dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JobDao implements Dao<Job, Integer>  {

    @Override
    public void create(Job job) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:./db", "sa", "");

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Job"
                + " (name, created, duedate, quantity, material, workloadestimate, details, customer)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        stmt.setString(1, job.getName());
        stmt.setTimestamp(2, Timestamp.valueOf(job.getCreated()));
        stmt.setTimestamp(3, Timestamp.valueOf(job.getDueDate().atTime(23,59)));
        stmt.setInt(4, job.getQuantity());
        stmt.setString(5, job.getMaterial());
        stmt.setDouble(6, job.getWorkloadEstimate());
        stmt.setString(7, job.getDetails());
        stmt.setString(8, job.getCustomer());

        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    @Override
    public Job read(Integer id) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:./db", "sa", "");

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Job WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) return null;

        Job j = parseJobFromResult(rs);

        stmt.close();
        rs.close();
        connection.close();

        return j;
    }

    @Override
    public Job update(Job job, Integer id) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:./db", "sa", "");

        PreparedStatement stmt = connection.prepareStatement("UPDATE Job set"
                + " name = ?, duedate = ?, quantity = ?, material = ?, workloadestimate = ?, details = ?, customer = ?"
                + " where id = ?;");
        stmt.setString(1, job.getName());
        stmt.setTimestamp(2, Timestamp.valueOf(job.getDueDate().atTime(23,59)));
        stmt.setInt(3, job.getQuantity());
        stmt.setString(4, job.getMaterial());
        stmt.setDouble(5, job.getWorkloadEstimate());
        stmt.setString(6, job.getDetails());
        stmt.setString(7, job.getCustomer());

        stmt.setInt(8, id);

        stmt.executeUpdate();
        stmt.close();
        connection.close();

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

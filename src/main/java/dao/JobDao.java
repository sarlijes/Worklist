package dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JobDao implements Dao<Job, Integer> {

    private Connection connection;

    public JobDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Job create(Job job) throws SQLException {


        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Job"
                + " (name, created, duedate, quantity, material, workloadestimate, details, customer)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        stmt.setString(1, job.getName());
        stmt.setTimestamp(2, Timestamp.valueOf(job.getCreated()));
        stmt.setTimestamp(3, job.getDueDate() != null ?
                Timestamp.valueOf(job.getDueDate().atTime(23, 59)) : null);
        stmt.setInt(4, job.getQuantity());
        stmt.setString(5, job.getMaterial());
        stmt.setDouble(6, job.getWorkloadEstimate());
        stmt.setString(7, job.getDetails());
        stmt.setString(8, job.getCustomer());

        stmt.executeUpdate();

        int id = -1;
        ResultSet generatedKeys = stmt.getGeneratedKeys();

        if (generatedKeys.next()) id = generatedKeys.getInt(1);

        generatedKeys.close();

        stmt.close();
        return read(id);
    }

    @Override
    public Job read(Integer id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Job WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) return null;

        Job j = parseJobFromResult(rs);

        stmt.close();
        rs.close();

        return j;
    }

    @Override
    public Job update(Job job, Integer id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE Job set"
                + " name = ?, duedate = ?, quantity = ?, material = ?, workloadestimate = ?, details = ?, customer = ?"
                + " where id = ?;");
        stmt.setString(1, job.getName());
        stmt.setTimestamp(2, Timestamp.valueOf(job.getDueDate().atTime(23, 59)));
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

    public Job markAsDone(Integer id, Double workloadActual) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE Job set"
                + " workloadactual = ?, finished = ?"
                + " where id = ?;");
        stmt.setDouble(1, workloadActual != null ? workloadActual : null);
        stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
        stmt.setInt(3, id);

        stmt.executeUpdate();
        stmt.close();

        return null;
    }

    public Job markAsNotDone(Integer id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE Job set finished = null, workloadactual = null"
                + " where id = ?;");
        stmt.setInt(1, id);

        stmt.executeUpdate();
        stmt.close();

        return null;
    }


    @Override
    public void delete(Integer id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE from Job where id = ?;");
        stmt.setInt(1, id);

        stmt.executeUpdate();
        stmt.close();
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
        Double workloadEstimate = resultSet.getDouble("workloadEstimate");
        Double workloadActual = resultSet.getDouble("workloadActual");
        String details = resultSet.getString("details");
        String customer = resultSet.getString("customer");

        Job j = new Job(id, name, created, dueDate, finished, deleted, quantity, material,
                workloadEstimate, workloadActual, details, customer);

        return j;
    }

    @Override
    public List<Job> list() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Job ORDER BY finished");
        ResultSet resultSet = statement.executeQuery();
        List<Job> jobs = new ArrayList<>();

        while (resultSet.next()) {
            jobs.add(parseJobFromResult(resultSet));
        }
        return jobs;
    }

}

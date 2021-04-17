import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import dao.Job;
import dao.JobDao;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class WorklistTest {

    private static JobDao dao;
    private static Connection connection;

    @BeforeClass
    public static void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:");

        dao = new JobDao(connection);

        PreparedStatement dropTableStatement = connection.prepareStatement("" +
                "DROP TABLE if exists Job;");
        dropTableStatement.executeUpdate();
        dropTableStatement.close();

        PreparedStatement createStatement = connection.prepareStatement("" +
                "CREATE TABLE if not exists Job (id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "created DATETIME,\n" +
                "finished DATETIME,\n" +
                "deleted DATETIME,\n" +
                "duedate DATETIME,\n" +
                "name VARCHAR(1024),\n" +
                "quantity INTEGER,\n" +
                "material VARCHAR(1024),\n" +
                "workloadestimate FLOAT,\n" +
                "workloadactual FLOAT,\n" +
                "details VARCHAR(2048),\n" +
                "customer VARCHAR(1024)\n" +
                ");");
        createStatement.executeUpdate();
        createStatement.close();

        PreparedStatement stmt = connection.prepareStatement("" +
                "insert into job (name, created, duedate, quantity, material, workloadestimate, details, customer) values \n" +
                "('Test data 1', '2021-02-23T00:00:00.0', '2021-04-04T00:00:00.0', 5.0, 'Test data material X', 4.0, 'details', 'Customer X');\n" +
                "\n" +
                "insert into job (name, created, duedate, quantity, material, workloadestimate, details, customer) values \n" +
                "('Test data 2', now(), '2021-08-08T00:00:00.0', 45.0, 'AlNiCo', 2.0, 'Test data material Z', 'Customer Y');\n" +
                "\n" +
                "insert into job (name, created, duedate, quantity, material, workloadestimate, details, customer) values \n" +
                "('Test data 2', now(), '2021-02-02T00:00:00.0', 2.0, 'CU-OF', 1.0, 'Test data material Y', 'internal');");

        stmt.executeUpdate();
        stmt.close();

    }

    @AfterClass
    public static void closeTestConnection() throws SQLException {
        connection.close();
    }

    private Job createTestJob() {
        return new Job("name",
                LocalDate.parse("2021-08-20"),
                12,
                "material",
                2.0,
                "details",
                "customer");
    }

    @Test
    public void jobConstructorWorks() {

        Job job = createTestJob();

        assert (job != null);
        assertEquals("name", job.getName());
        assertTrue(isSameDay(LocalDateTime.now(), job.getCreated()));
        assertEquals(LocalDate.parse("2021-08-20"), job.getDueDate());
        assertEquals(null, job.getFinished());
        assertFalse(job.isFinished());
        assertEquals(null, job.getDeleted());
        assertEquals(12, job.getQuantity());
        assertEquals("material", job.getMaterial());
        assertEquals(null, job.getWorkloadActual());
        assertEquals("details", job.getDetails());
        assertEquals("customer", job.getCustomer());
        assertThat(job.getWorkloadEstimate(), is(2.0));
    }

    public static boolean isSameDay(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        LocalDate localDate1 = dateTime1.toLocalDate();
        LocalDate localDate2 = dateTime2.toLocalDate();
        return localDate1.isEqual(localDate2);
    }

    @Test
    public void helperMethodIsSameDayWorks() {
        LocalDateTime dateTime1 = LocalDateTime.parse("2021-02-20T06:30:00");
        LocalDateTime dateTime2 = LocalDateTime.parse("2021-02-20T08:30:00");

        assertTrue(isSameDay(dateTime1, dateTime2));
        assertFalse(isSameDay(dateTime1, dateTime1.minusDays(1)));
    }

    @Test
    public void createdJobHasTheAttributesItWasCreatedWith() throws SQLException {
        Job job = createTestJob();
        Job jobInDatabase = dao.create(job);

        assertEquals("name", jobInDatabase.getName());
        assertTrue(isSameDay(LocalDateTime.now(), job.getCreated()));
        assertEquals(LocalDate.parse("2021-08-20"), jobInDatabase.getDueDate());
        assertEquals(null, jobInDatabase.getFinished());
        assertFalse(jobInDatabase.isFinished());
        assertEquals(null, jobInDatabase.getDeleted());
        assertEquals(12, jobInDatabase.getQuantity());
        assertEquals("material", jobInDatabase.getMaterial());
        assertEquals("details", jobInDatabase.getDetails());
        assertEquals("customer", jobInDatabase.getCustomer());

        assertThat(jobInDatabase.getWorkloadEstimate(), is(2.0));
    }

    @Test
    public void canMarkJobAsDone() throws SQLException {

        Job j = dao.list().get(2);
        assertTrue(j != null);
        assertFalse(j.isFinished());
        int jobId = j.getId();

        dao.markAsDone(jobId, 5.0);
        assertTrue(dao.read(j.getId()).isFinished());

    }

}
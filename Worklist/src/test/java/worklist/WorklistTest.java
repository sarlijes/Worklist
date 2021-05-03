package worklist;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import worklist.dao.EmployeeDao;
import worklist.dao.MaterialDao;
import worklist.dao.SQLUtils;
import worklist.entity.Employee;
import worklist.entity.Job;
import worklist.dao.JobDao;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class WorklistTest {

    private static JobDao jobDao;
    private static EmployeeDao employeeDao;
    private static MaterialDao materialDao;
    private static Connection connection;
    private static SQLUtils sqlUtils;

    @BeforeClass
    public static void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:");
        jobDao = new JobDao(connection);
        employeeDao = new EmployeeDao(connection);
        materialDao = new MaterialDao(connection);
        sqlUtils = new SQLUtils();
        sqlUtils.createTables(connection);

        PreparedStatement stmt = connection.prepareStatement("" +
                "insert into job (name, created, duedate, quantity, material, workloadestimate, details, customer) values \n" +
                "('Test data 1', '2021-02-23T00:00:00.0', '2021-04-04T00:00:00.0', 5.0, 'Test data material X', 4.0, 'details', 'Customer X');\n" +
                "\n" +
                "insert into job (name, created, duedate, quantity, material, workloadestimate, details, customer) values \n" +
                "('Test data 2', now(), '2021-08-08T00:00:00.0', 45.0, 'AlNiCo', 2.0, 'Test data material Z', 'Customer Y');" +
                "\n" +
                "insert into job (name, created, duedate, quantity, material, workloadestimate, details, customer) values \n" +
                "('Test data 2', now(), '2021-02-02T00:00:00.0', 2.0, 'CU-OF', 1.0, 'Test data material Y', 'internal');" +

                "insert into material (name, details) values ('test material name 1', 'test material details');" +
                "insert into material (name, details) values ('test material name 2', 'test material details');" +
                "insert into material (name, details) values ('test material name 3', 'test material details');");

        stmt.executeUpdate();
        stmt.close();

        addTestEmployees();

    }

    private static void addTestEmployees() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "insert into employee (username, password) " +
                        "values ('svenSvensson', 'superSecretPassword1');" +
                        "insert into employee (username, password) " +
                        "values ('ragnar', 'superSecretPassword2')" +
                        ";");
        stmt.executeUpdate();
        stmt.close();
    }

    private Employee randomEmployeeFromDatabase() throws SQLException {
        List<Employee> employees = employeeDao.list();
        Random random = new Random();
        return employees.get(random.nextInt(employees.size()));
    }

    private Job createTestJob() throws SQLException {
        return createTestJob(randomEmployeeFromDatabase());
    }

    private Job createTestJob(Employee creator) throws SQLException {
        return new Job("name",
                LocalDate.parse("2021-08-20"),
                12,
                materialDao.readByName("test material name 1"),
                2.0,
                "details",
                "customer",
                creator);
    }

    @Test
    public void jobConstructorWorks() throws SQLException {

        Job job = createTestJob();

        assert (job != null);
        assertEquals("name", job.getName());
        assertTrue(isSameDay(LocalDateTime.now(), job.getCreated()));
        assertEquals(LocalDate.parse("2021-08-20"), job.getDueDate());
        assertEquals(null, job.getFinished());
        assertFalse(job.isFinished());
        assertEquals(null, job.getDeleted());
        assertEquals(12, job.getQuantity());
        assertEquals("test material name 1", job.getMaterial().getName());
        assertEquals(null, job.getWorkloadActual());
        assertEquals("details", job.getDetails());
        assertEquals("customer", job.getCustomer());
        assertThat(job.getWorkloadEstimate(), is(2.0));

    }

    @Test
    public void createdJobHasTheAttributesItWasCreatedWith() throws SQLException {
        Job job = createTestJob();
        Job jobInDatabase = jobDao.create(job);

        assertEquals("name", jobInDatabase.getName());
        assertTrue(isSameDay(LocalDateTime.now(), job.getCreated()));
        assertEquals(LocalDate.parse("2021-08-20"), jobInDatabase.getDueDate());
        assertEquals(null, jobInDatabase.getFinished());
        assertFalse(jobInDatabase.isFinished());
        assertEquals(null, jobInDatabase.getDeleted());
        assertEquals(12, jobInDatabase.getQuantity());
        assertEquals("test material name 1", jobInDatabase.getMaterial().getName());
        assertEquals("details", jobInDatabase.getDetails());
        assertEquals("customer", jobInDatabase.getCustomer());

        assertThat(jobInDatabase.getWorkloadEstimate(), is(2.0));
    }

    @Test
    public void canMarkJobAsDoneWithWorkTimeActualSpecified() throws SQLException {

        Job j = jobDao.list().get(0);
        assertTrue(j != null);
        assertFalse(j.isFinished());
        int jobId = j.getId();

        jobDao.markAsDone(jobId, 5.0);
        assertTrue(jobDao.read(j.getId()).isFinished());

    }

    @Test
    public void canMarkJobAsDoneWithWorktimeActualAsNull() throws SQLException {

        Job j = jobDao.list().get(2);
        assertTrue(j != null);
        assertFalse(j.isFinished());
        int jobId = j.getId();

        jobDao.markAsDone(jobId, null);
        assertTrue(jobDao.read(j.getId()).isFinished());

    }

    @Test
    public void canMarkJobAsNotDone() throws SQLException {
        Job j = jobDao.list().get(1);
        assertTrue(j != null);
        assertFalse(j.isFinished());

        jobDao.markAsDone(j.getId(), 5.0);
        jobDao.markAsNotDone(j.getId());
        assertFalse(jobDao.read(j.getId()).isFinished());
    }

    @Test
    public void canDeleteJob() throws SQLException {
        Job j = jobDao.list().get(1);
        assertTrue(j != null);

        jobDao.delete(j.getId());

        assertEquals(0, jobDao.list().stream()
                .filter(job -> job.getId() == j.getId())
                .count());
    }


    @Test
    public void editingJobWorks() throws SQLException {
        Job job = jobDao.create(createTestJob());
        assertTrue(job != null);

        Job jobToEdit = createTestJob();
        jobToEdit.setName("Another name");
        jobToEdit.setDueDate(LocalDate.parse("2021-12-31"));
        jobToEdit.setQuantity(555);
        jobToEdit.setMaterial(materialDao.readByName("test material name 1"));
        jobToEdit.setWorkloadEstimate(12.5);
        jobToEdit.setDetails("details and more details");
        jobToEdit.setCustomer("Another customer");

        job = jobDao.update(jobToEdit, job.getId());

        assertTrue(job.getName().equals("Another name"));
        assertTrue(job.getDueDate().equals(LocalDate.parse("2021-12-31")));
        assertTrue(job.getQuantity() == 555);
        assertTrue(job.getMaterial().getName().equals("test material name 1"));
        assertThat(job.getWorkloadEstimate(), is(12.5));
        assertTrue(job.getDetails().equals("details and more details"));
        assertTrue(job.getCustomer().equals("Another customer"));
    }

    @Test
    public void canParseJobFromResultSetWhenManyAttributesAreNull() throws SQLException {

        Job job = jobDao.create(new Job("name",
                null,
                12,
                materialDao.readByName("test material name 1"),
                2.0,
                "details",
                "customer",
                null));
        assert (job != null);
        assertTrue(job.getDueDate() == null);
        assertTrue(job.getDeleted() == null);
        assertTrue(job.getCreator() == null);

    }

    @Test
    public void creatorEmployeeIsSavedCorrectly() throws SQLException {

        Employee randomEmployee = randomEmployeeFromDatabase();
        assert (randomEmployee != null);

        Job j = createTestJob(randomEmployee);
        assert (j != null);

        assertEquals(randomEmployee.getUsername(), j.getCreatorName());
        assertEquals(randomEmployee.getUsername(), j.getCreator().getUsername());

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

        // TODO extend tests to include corner cases

        assertTrue(isSameDay(dateTime1, dateTime2));
        assertFalse(isSameDay(dateTime1, dateTime1.minusDays(1)));
    }

    @AfterClass
    public static void closeTestConnection() throws SQLException {
        connection.close();
    }

}
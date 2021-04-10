import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import dao.Job;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class WorklistTest {

    private Job job;

    @Before
    public void setUp() {

        job = new Job(1,
                "name",
                LocalDateTime.parse("2021-02-20T06:30:00"),
                LocalDate.parse("2021-04-04"),
                null,
                null,
                12,
                "material",
                2.0,
                null,
                "details",
                "customer");
    }

    @Test
    public void createdJobExists() {
        assert(job != null);
    }

    @Test
    public void createdJobHasTheAttributesItWasCreatedWith() {

        assertEquals(1, job.getId());
        assertEquals("name", job.getName());
        assertEquals(LocalDateTime.parse("2021-02-20T06:30:00"), job.getCreated());
        assertEquals(LocalDate.parse("2021-04-04"), job.getDueDate());
        assertEquals(null , job.getFinished());
        assertEquals(null , job.getDeleted());
        assertEquals(12 , job.getQuantity());
        assertEquals("material" , job.getMaterial());
        assertEquals(null , job.getWorkloadActual());
        assertEquals("details" , job.getDetails());
        assertEquals("customer" , job.getCustomer() );

        assertThat(job.getWorkloadEstimate(), is(2.0));
    }

}
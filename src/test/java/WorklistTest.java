import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
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
        assertTrue(job != null);
    }

    @Test
    public void createdJobHasTheAttributesItWasCreatedWith() {
        assertThat(job.getId(), is(1));
        assertThat(job.getName(), is("name"));
        assertThat(job.getCreated(), is(LocalDateTime.parse("2021-02-20T06:30:00")));
        assertThat(job.getDueDate(), is(LocalDate.parse("2021-04-04")));
        assertThat(job.getFinished(), is(equalTo(null)));
        assertThat(job.getDeleted(), is(equalTo(null)));
        assertThat(job.getQuantity(), is(12));
        assertThat(job.getMaterial(), is("material"));
        assertThat(job.getWorkloadEstimate(), is(2.0));
        assertThat(job.getWorkloadActual(), is(equalTo(null)));
        assertThat(job.getDetails(), is("details"));
        assertThat(job.getCustomer(), is("customer"));
    }

}
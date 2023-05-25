package jobdataportal;

import org.junit.Test;
import static org.junit.Assert.*;

public class JobDataTest {
    public JobData createMockJobData() {
        JobData mockJobData = new JobData();
        mockJobData.setCompany("Google");
        mockJobData.setCurrency("USD");
        mockJobData.setLocation("Sunny Valley, CA");
        mockJobData.setPosition("Software Engineer");
        mockJobData.setSalary(100000);
        mockJobData.setSeniority("Senior");
        mockJobData.setStack("Java");
        return mockJobData;
    }

    @Test public void testToString() {
        JobData classUnderTest = createMockJobData();
        assertNotNull("class able to gather job data", classUnderTest.toString());
    }
}

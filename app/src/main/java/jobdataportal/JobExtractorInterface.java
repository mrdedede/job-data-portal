package jobdataportal;

import java.util.List;

public interface JobExtractorInterface {
    
    abstract List<String> getJobURLs(String endpoint);

    abstract List<JobData> getJobObjects(String endpoint);

}

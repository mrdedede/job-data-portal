package jobdataportal;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JobExtractorWWR implements JobExtractorInterface{
    
    /**
     * 
     * @return
     */
    public List<String> getJobURLs(String wwrEndpoint) {
        List<String> hrefList = new ArrayList<>();
        WebExtractor wwr = new WebExtractor(wwrEndpoint);
        wwr.doHttpRequest();
        Elements wwrATags = wwr.searchMultipleElements("li.feature a");
        for (Element jobPosting : wwrATags) {
            String curHref = jobPosting.attr("href");
            if (curHref.contains("remote-jobs")) {
                hrefList.add(wwrEndpoint+curHref);
            }
        }
        return hrefList;
    }

    /**
     * 
     * @return
     */
    public List<JobData> getJobObjects(String wwrEndpoint) {
        List<String> jobUrls = getJobURLs(wwrEndpoint);
        List<JobData> jobDataList = new ArrayList<>();
        for(String jobUrl: jobUrls) {
            try {
                WebExtractor curJobReq = new WebExtractor(jobUrl);
                JobData curJobData = new JobData();
                curJobReq.doHttpRequest();

                // Gets the Company name
                Element nameElement = curJobReq.searchSingleElement("div.company-card h2 a");
                curJobData.setCompany(nameElement.text());

                // Gets the position
                Element positionElement = curJobReq.searchSingleElement("div.listing-header-container h1");
                getJobPosition(curJobData, positionElement);

                // Trying to fetch information form Job Tags
                Elements jobTags = curJobReq.searchMultipleElements("span.listing-tag");
                processJobTags(curJobData, jobTags);

                jobDataList.add(curJobData);
            } catch (NullPointerException e) {
                System.out.println("Failed for "+jobUrl);
            }
        }

        return jobDataList;
    }

    /**
     * 
     * @param job
     * @param tags
     */
    private void processJobTags(JobData job, Elements tags) {
        for (Element tag : tags) {
            if (tag.text().contains("Only") || tag.text().contains("UTC")) {
                job.setLocation(tag.text());
            }
            if (tag.text().contains("USD") || tag.text().contains("$")) {
                job.setCurrency("USD");
            } else if (tag.text().contains("EUR")) {
                job.setCurrency("EUR");
            } else if (tag.text().contains("GBP") || tag.text().contains("£")) {
                job.setCurrency("GBP");
            }
        }
    }

    /**
     * 
     * @param job
     * @param tag
     */
    private void getJobPosition(JobData job, Element tag) {
        String curText = tag.text();
        curText = curText.replaceFirst("\\bRemote \\b", "");
        if(curText.contains("Junior")) {
            curText = curText.replaceFirst("\\bJunior(?:/|\\s)\\b", "");
            job.setSeniority("Junior");
        } else if(curText.contains("Staff")) {
            curText = curText.replaceFirst("\\bStaff(?:/|\\s)\\b", "");
            job.setSeniority("Staff");
        } else if(curText.contains("Senior")) {
            curText = curText.replaceFirst("\\bSenior(?:/|\\s)\\b", "");
            job.setSeniority("Senior");
        }
        curText = curText.replaceFirst("\\bSenior(?:/|\\s)\\b", "");
        job.setPosition(curText);
    }
}

package jobdataportal;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;

public class JobExtractorService {
    
    /**
     * 
     * @return
     */
    public List<String> getWWRJobURLs() {
        String url = "https://weworkremotely.com";
        List<String> hrefList = new ArrayList<>();
        WebExtractor wwr = new WebExtractor(url);
        wwr.doHttpRequest();
        wwr.searchMultipleElements("li.feature a");
        for (Element jobPosting : wwr.getCurElements()) {
            String curHref = jobPosting.attr("href");
            if (curHref.contains("remote-jobs")) {
                hrefList.add(url+curHref);
            }
        }
        return hrefList;
    }

    /**
     * 
     * @return
     */
    public List<JobData> getWWRJobs() {
        List<String> jobUrls = getWWRJobURLs();
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
                curJobData.setPosition(positionElement.text());

                jobDataList.add(curJobData);
            } catch (NullPointerException e) {
                System.out.println("Failed for "+jobUrl);
            }
        }

        return jobDataList;
    }

}

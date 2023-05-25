package jobdataportal;

import java.io.IOException;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class WebExtractor {
    private String url;
    private Document doc;

    WebExtractor(String url) {
        this.url = url;
    }

    /**
     * 
     * @return
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * 
     * @return
     */
    public Document getDoc() {
        return this.doc;
    }
    
    /**
     * 
     */
    public void doHttpRequest() {
        try {
            Document doc = Jsoup.connect(url).get();
            this.doc = doc;
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    /**
     * 
     * @param htmlElements
     */
    public Elements searchMultipleElements(String htmlElements) {
        Elements elements = this.doc.select(htmlElements);
        return elements;
    }

    /**
     * 
     * @param htmlElements
     * @return
     */
    public Element searchSingleElement(String htmlElements) {
        Element element = this.doc.selectFirst(htmlElements);
        return element;
    }

}

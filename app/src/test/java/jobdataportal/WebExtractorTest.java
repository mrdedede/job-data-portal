/**
 * 
 */
package jobdataportal;

import org.junit.Test;
import static org.junit.Assert.*;

public class WebExtractorTest {
    @Test public void appHasAGreeting() {
        WebExtractor classUnderTest = new WebExtractor("https://weworkremotely.com");
        classUnderTest.doHttpRequest();
        assertNotNull("app should get the http page", classUnderTest.getDoc());
    }
}

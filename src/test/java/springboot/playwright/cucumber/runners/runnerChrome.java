package springboot.playwright.cucumber.runners;

import io.cucumber.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("/features")
@ConfigurationParameter(key="cucumber.plugin", value = "html:target/CucumberTestExecutionReport_Chrome.html")
@Slf4j
public class runnerChrome {
    @Before
    public void setUp() {
        //You could pass these property values using application.properties file also.
        //I am writing it in this way to make it easy to understand for those who have less technical knowledge
        //and willing to learn and experiment more
        System.setProperty("browser","chrome");
        System.setProperty("tracing", String.valueOf(true));
        log.info("Passing browser property to PlaywrightBrowser bean");
    }

}

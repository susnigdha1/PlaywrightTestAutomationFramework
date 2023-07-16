package springboot.playwright.cucumber.runners;

import io.cucumber.java.Before;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("/features")
@ConfigurationParameter(key="cucumber.plugin", value = "html:target/CucumberTestExecutionReport_Firefox.html")
public class runnerFirefox {
    @Before
    public void setBrowser() {
        System.setProperty("browser","firefox");
    }
}
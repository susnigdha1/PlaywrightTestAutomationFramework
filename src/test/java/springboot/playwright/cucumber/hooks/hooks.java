package springboot.playwright.cucumber.hooks;
import com.microsoft.playwright.Tracing;
import io.cucumber.java.Scenario;
import lombok.extern.slf4j.Slf4j;
import springboot.playwright.cucumber.playwright.PlaywrightBrowser;
import io.cucumber.java.After;

import java.nio.file.Paths;

@Slf4j
public class hooks {
  PlaywrightBrowser playwrightBrowser;
    hooks(PlaywrightBrowser browser) {
        this.playwrightBrowser=browser;
    }

    @After
    public void afterScenario(Scenario scenario)
    {
        //Capturing screenshot irrespective of whether the test pass or fail
        //This approach will ensure there is always evidences for the tests outcome
        scenario.attach(playwrightBrowser.captureScreenshot(),"image/png","screenshot");
        log.debug("Attaching full page screenshot, after the scenario");

        if(playwrightBrowser.isTracingOptionSet()) {
            playwrightBrowser.getPlaywrightBrowserContext().tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get("target/traces.zip")));
        }
    }
}

package springboot.playwright.cucumber.hooks;
import io.cucumber.java.Scenario;
import springboot.playwright.cucumber.playwright.PlaywrightBrowser;
import io.cucumber.java.After;

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
    }
}

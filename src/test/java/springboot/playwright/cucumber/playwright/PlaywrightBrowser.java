package springboot.playwright.cucumber.playwright;

import com.microsoft.playwright.*;

/** Using Factory design pattern
 */
public interface PlaywrightBrowser {
    void setPlaywrightBrowser(String browserType);
    Browser getPlaywrightBrowser();
    void setPlaywrightBrowserContext();
    BrowserContext getPlaywrightBrowserContext(); //It will be used to test scenarios with different browser tabs/windows
    void setPlaywrightPage();
    Page getPlaywrightPage();
    byte[] captureScreenshot();
    void close();
    void setTracing(Boolean option);
    boolean isTracingOptionSet();
}

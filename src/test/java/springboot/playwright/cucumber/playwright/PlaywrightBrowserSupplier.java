package springboot.playwright.cucumber.playwright;

import com.microsoft.playwright.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PlaywrightBrowserSupplier implements PlaywrightBrowser {
    Playwright playwright = Playwright.create();
    Browser browser;
    BrowserContext browserContext;
    Page page;

    public PlaywrightBrowserSupplier(String BrowserType){
        setPlaywrightBrowser(BrowserType);
    }

    /** This method will initialize the PlaywrightBrowser bean (Facade design pattern) with appropriate browser type
     * Author: Susnigdha Chatterjee
     */
    @Override
    public void setPlaywrightBrowser(String browserType)
    {
        switch(browserType.toLowerCase()) {
            case "chrome":
                //Below line will launch chrome browser in non-headless mode
                //browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
                //If you wish to execute the code in Headless mode then please un comment below line*/
                browser = playwright.chromium().launch();
                break;
            case "firefox":
                browser = playwright.firefox().launch();
                break;
            default:
                browser = playwright.chromium().launch();
                break;
        }
        setPlaywrightBrowserContext();
        setPlaywrightPage();
    }

    @Override
    public Browser getPlaywrightBrowser() {
        return browser;
    }

    @Override
    public void setPlaywrightBrowserContext() {
        browserContext=getPlaywrightBrowser().newContext();

    }

    @Override
    public BrowserContext getPlaywrightBrowserContext() {
        return browserContext;}

    @Override
    public void setPlaywrightPage(){
        page=getPlaywrightBrowserContext().waitForPage(()->getPlaywrightBrowserContext().newPage());
    }

    /**This method will help to retrieve the Playwright page from the PlaywrightBrowser bean
     * @return
     */
    @Override
    public Page getPlaywrightPage(){
        page.onRequest(request -> assertEquals(200, request.response().status()));
        return page;
    }

    @Override
    public void close() {
        page.close();
        browserContext.close();
        playwright.close();
    }

    /**
     * This method will be called from Hooks class, from cucumber After hook
     * @Author Susnigdha Chatterjee
     * @return byte array which holds the screenshot
     */
    @Override
    public byte[] captureScreenshot(){
        Path objPath = Paths.get("target/screenshots/Screenshot_"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyy_hhmmss"))+".png");
        return page.screenshot(new Page.ScreenshotOptions().setPath(objPath).setFullPage(true));
    }
}

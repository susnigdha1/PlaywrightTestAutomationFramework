package springboot.playwright.cucumber.playwright;

import com.microsoft.playwright.*;
import java.util.Objects;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@SuppressWarnings({"unused","cast"})
public class PlaywrightBrowserSupplier implements PlaywrightBrowser {
    Playwright playwright = Playwright.create();
    Browser browser;
    BrowserContext browserContext;
    Page page;
    //This boolean value is used for introducing Playwright tracing
    Boolean isTracingSet=false;

    public PlaywrightBrowserSupplier(String BrowserType, Optional<Boolean> tracingOption){
        setPlaywrightBrowser(BrowserType);
        //This method checks the optional tracingOption value and accordingly turns on Playwright browser tracing
        setTracing(tracingOption.orElse(false));
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
     */
    @Override
    public Page getPlaywrightPage(){
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
     * @author Susnigdha Chatterjee
     * @return byte array which holds the screenshot
     */
    @Override
    public byte[] captureScreenshot(){
        Path objPath = Paths.get("target/screenshots/Screenshot_"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyy_hhmmss"))+".png");
        return page.screenshot(new Page.ScreenshotOptions().setPath(objPath).setFullPage(true));
    }

    /**
     * This method will be called from Constructor to set the Playwright browser tracing on
     * @author Susnigdha Chatterjee
     * @return byte array which holds the screenshot
     */
    @Override
    public void setTracing(Boolean option){
        if(option && !Objects.isNull(browserContext)){
            browserContext.tracing().start(new Tracing.StartOptions()
                    .setSnapshots(true));
            isTracingSet = true;
        }
    }
    /**
     * This method will be called from Hooks class's After annotation to decide if Playwright browser tracing needs to be turned off
     * @author Susnigdha Chatterjee
     * @return byte array which holds the screenshot
     */
    @Override
    public boolean isTracingOptionSet(){
        return isTracingSet;
    }
}

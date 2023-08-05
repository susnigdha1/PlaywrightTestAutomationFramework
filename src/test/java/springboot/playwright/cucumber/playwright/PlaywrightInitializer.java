package springboot.playwright.cucumber.playwright;

import io.cucumber.spring.ScenarioScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Optional;

/**
 * Using factory design pattern. The PlaywrightBrowser interface serves as the factory.
 * PlaywrightBrowserSupplier implements the PlaywrightBrowser interface based on
 * the browser type supplied using the browserType parameter of testconfigurations.properties file
 */

@Configuration
@Slf4j
@SuppressWarnings({"unused","cast"})
public class PlaywrightInitializer {
    /** Using Facade design pattern to initialize Playwright page using custom built PlaywrightBrowser interface
     * @author Susnigdha Chatterjee
    */
   @Bean(name="PlaywrightBrowser", destroyMethod = "close")
   @ScenarioScope
   public PlaywrightBrowser init() {
       //Creating the bean of type PlaywrightBrowser using its implementing class PlaywrightBrowserPage
       log.info("Creating PlaywrightBrowser bean");
       //Added new Optional parameter to the constructor method
       return new PlaywrightBrowserSupplier(System.getProperty("browser"), Optional.of(Boolean.valueOf(System.getProperty("tracing"))));
   }
}

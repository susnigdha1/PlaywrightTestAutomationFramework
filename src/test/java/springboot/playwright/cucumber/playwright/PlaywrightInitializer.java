package springboot.playwright.cucumber.playwright;

import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Using factory design pattern. The PlaywrightBrowser interface serves as the factory.
 * PlaywrightBrowserSupplier implements the PlaywrightBrowser interface based on
 * the browser type supplied using the browserType parameter of testconfigurations.properties file
 */

@Configuration
public class PlaywrightInitializer {
    /** Using Facade design pattern to initialize Playwright page using custom built PlaywrightBrowser interface
     * @Author: Susnigdha Chatterjee
    */
   @Bean(name="PlaywrightBrowser", destroyMethod = "close")
   @ScenarioScope
   public PlaywrightBrowser init() {
       //Creating the bean of type PlaywrightBrowser using its implementing class PlaywrightBrowserPage
       return new PlaywrightBrowserSupplier(System.getProperty("browser"));
   }
}

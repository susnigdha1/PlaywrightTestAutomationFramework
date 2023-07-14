package springboot.playwright.cucumber.playwright;

import io.cucumber.spring.ScenarioScope;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

/**
 * Using factory design pattern. The PlaywrightBrowser interface serves as the factory.
 * PlaywrightBrowserSupplier implements the PlaywrightBrowser interface based on
 * the browser type supplied using the browserType parameter of testconfigurations.properties file
 */

@Configuration
@PropertySource("classpath:testconfigurations.properties") //Linking test properties
public class PlaywrightInitializer {
   @Value("#{'${browserType}'.split(',')}") //Reading property from test.properties file
    public List<String> browserType;
    /** Using Facade design pattern to initialize Playwright page using custom built PlaywrightBrowser interface
     * @Author: Susnigdha Chatterjee
    */
   @Bean(name="PlaywrightBrowser", destroyMethod = "close")
   @ScenarioScope
   public PlaywrightBrowser init() {
       //Creating the bean of type PlaywrightBrowser using its implementing class PlaywrightBrowserPage
       return new PlaywrightBrowserSupplier(browserType.get(0));
   }
}

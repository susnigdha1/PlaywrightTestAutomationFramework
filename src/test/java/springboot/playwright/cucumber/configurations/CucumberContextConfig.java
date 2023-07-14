package springboot.playwright.cucumber.configurations;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes={TestConfig.class})
public class CucumberContextConfig {
}

package springboot.playwright.cucumber.pageObjects;

import com.microsoft.playwright.options.AriaRole;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import springboot.playwright.cucumber.playwright.PlaywrightBrowser;
import com.microsoft.playwright.Page;
import io.cucumber.spring.ScenarioScope;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Component
@ScenarioScope
@PropertySource("classpath:testconfigurations.properties")
public class PageObject {
    @Value("${user_name}")
    String uname;
    @Value("${password}")
    String pwd;

    //Facade object to hold the browser context
    PlaywrightBrowser playwrightBrowser;
    Page page;
    //Using constant xpath locators
    final String username = "//input[@data-test='username']";
    final String password = "//input[@data-test='password']";

    //Following constructor based dependency injection mechanism
    public PageObject(PlaywrightBrowser browser){
        this.playwrightBrowser=browser;
        this.page = playwrightBrowser.getPlaywrightPage();
    }
    //Navigate to the application url
    public void navigateToUrl(String url) {
        page.navigate(url);
    }
    //Perform login
    public void login()
    {
        //Using xpath locators, defined as constant above
        page.locator(username).fill(uname);
        page.locator(password).fill(pwd);
        //Using text based identification to locate the Login button
        page.getByText("Login").click();
    }

    //Using Dynamic XPath mechanism to select a product to purchase based on the given name.
    //Remember Playwright does not supply any locator to identify an element by 'class' name.
    public void selectProductByName(String productName)
    {
        String xpath = "//div[@class=\"inventory_item\" and .//div[text()=\""+productName+"\"]]//button[text()='Add to cart']";
        page.waitForCondition(() -> page.locator(xpath).isVisible());
        page.locator(xpath).click();
    }
    //Asserting the landing page displayed 6 items for making a purchase
    public void verifyLandingPageIsDisplayed()
    {
        //Using Playwright assertion.
        // Remember Playwright does not supply any inbuilt locator to identify an element by 'class' name. Use xpath or CSS.
        //The locator xpath will return a list of elements
        assertThat(page.locator("//div[@class=\"inventory_item\"]")).hasCount(6);
    }

    //Click on the Shopping Cart Icon
    public void clickOnShoppingCart()
    {
        //Using CSS locator to click on the Shopping Cart
        page.locator(".shopping_cart_link:visible").click();
    }

    //Verify shopping cart based on the test data supplied on Cucumber
    public void verifyShoppingCartData(List<Map<String,String>> testdata)
    {
        //Using java8 Lamda notation
        page.waitForCondition(() -> page.getByText("Your Cart").isVisible());
        assertThat(page.getByText("Your Cart")).isVisible();
        //Spooky fact, if you change hasText() method to hasValue() method in below code
        //then Playwright will throw a timeout exception on the locator object.
        assertThat(page.locator(".cart_quantity")).hasText(testdata.get(0).get("Quantity"));
        assertThat(page.locator(".inventory_item_name")).hasText(testdata.get(0).get("ProductName"));
    }

    public void checkoutShoppingCart()
    {
        //If the given HTML for the element contains: data-test="checkout" then below method will throw error:
        //page.getByTestId("checkout").click();
        //Error {
        //  message='Timeout 30000ms exceeded.
        //=========================== logs ===========================
        //waiting for locator("internal:attr=[data-testid=\"checkout\"]")
        //============================================================
        //... }
        //If you notice carefully, Playwright looks for the attribute: "data-testid" by default and NOT "data-test"
        //However, Playwright does offer a way to configure the selector strategy.
        //Please read: https://playwright.dev/java/docs/api/class-selectors for further knowledge on this.
        //Below example demonstrates how to select an element with getByRole() locator
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(
                        Pattern.compile("checkout", Pattern.CASE_INSENSITIVE)))
                .click();

        //Post click on Checkout, it will be a good practice to verify the Checkout Info placeholder is displayed
        //Note, in this example, we are using getByPlaceholder() method to identify the locator
        //DOM example: <input class="form_input" placeholder="First Name" type="text" name="fName" autocorrect="off" autocapitalize="none">
        page.waitForCondition(()-> page.getByPlaceholder("First Name").isEnabled());
        page.getByPlaceholder("First Name").fill(RandomStringUtils.random(10, true, false));
        page.getByPlaceholder("Last Name").fill(RandomStringUtils.random(10, true, false));
        page.getByPlaceholder("Zip/Postal Code").fill(RandomStringUtils.random(6, false, true));
        //page.getByRole(AriaRole.TEXTBOX).getByText("Continue").click();
        page.getByRole(AriaRole.BUTTON,
                        new Page.GetByRoleOptions().setName(
                                Pattern.compile("continue", Pattern.CASE_INSENSITIVE)))
                .click();
        assertThat(page.getByText("Checkout: Overview")).isVisible();
        page.locator("[id=\"finish\"]").click();
        assertThat(page.getByText("Thank you for your order!")).isVisible();
    }
}

package springboot.playwright.cucumber.stepDefinitions;
import io.cucumber.java.en.And;
import org.springframework.scheduling.annotation.Async;
import springboot.playwright.cucumber.pageObjects.PageObject;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;

public class stepDefinitions {
    @Autowired
    PageObject objPageObject;

    @When("I launch {string} the application")
    public void iLaunchAnWebApplication(String val) {
      objPageObject.navigateToUrl(val);
    }

    @And("I log in with valid credentials")
    public void iLoginToTheApplication() {
        objPageObject.login();
    }

    @And("I land on product selection page")
    public void iLandOnProductSelectionPage()
    {
        objPageObject.verifyLandingPageIsDisplayed();
    }

    @And("I select {string} item from the list of available items for purchase")
    public void iSelectItemFromTheListOfAvailableItemsForPurchase(String productName) {
        objPageObject.selectProductByName(productName);
    }

    @And("I click on the shopping cart link")
    public void iClickOnTheShoppingCartLink()
    {
        objPageObject.clickOnShoppingCart();
    }

    @And("I verify that the following product and quantity are selected")
    public void iVerifyShoppingCartItems(List<Map<String, String>> testData)
    {
        objPageObject.verifyShoppingCartData(testData);
    }

    @And("I perform checkout")
    public void iPerformCheckout()
    {
        objPageObject.checkoutShoppingCart();
    }
}

package stepDefinitions;

import org.openqa.selenium.WebDriver;
import pages.OrderPlacementPage;
import utilities.DriverFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class OrderPlacementSteps {
    private WebDriver driver;
    private OrderPlacementPage orderPlacementPage;

    public OrderPlacementSteps() {
        this.driver = DriverFactory.getDriver();
        this.orderPlacementPage = new OrderPlacementPage(driver);
    }

    @When("User enters shipping details and gets quotes")
    public void user_enters_shipping_details() {
        orderPlacementPage.enterShippingDetailsAndGetQuotes("India", "Maharashtra", "411001");
    }

    @When("User proceeds to checkout")
    public void user_proceeds_to_checkout() {
        orderPlacementPage.proceedToCheckout();
    }

    @When("User enters billing details")
    public void user_enters_billing_details() {
        orderPlacementPage.enterBillingDetails("Kunal", "Digole", "Pune", "New York", "10001", "United States", "New York");
    }

    @When("User selects delivery details")
    public void user_selects_delivery_details() {
        orderPlacementPage.selectDeliveryDetails();
    }

    @When("User selects delivery method")
    public void user_selects_delivery_method() throws InterruptedException {
        orderPlacementPage.selectDeliveryMethod();
    }

    @When("User selects payment method and confirms order")
    public void user_selects_payment_method_and_confirms_order() throws InterruptedException {
        orderPlacementPage.selectPaymentMethod();
        orderPlacementPage.confirmOrder();
    }

    @Then("Order should be placed successfully")
    public void order_should_be_placed_successfully() {
        Assert.assertTrue(orderPlacementPage.isOrderPlaced(), "Order placement failed!");
    }
}

package stepDefinitions;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import pages.ProductDetailsPage;
import utilities.DriverFactory;
import io.cucumber.java.en.*;

public class ProductDetailsSteps {
    WebDriver driver;
    ProductDetailsPage productPage;

    public ProductDetailsSteps() {
        this.driver = DriverFactory.getDriver();
        this.productPage = new ProductDetailsPage(driver);
    }
    @Test(priority = 4)
    @Given("User is on the product details page")
    public void user_is_on_product_details_page() {
        driver.get("https://demo.opencart.com.gr/index.php?route=product/product&product_id=31&search=Camera&description=true");
    }

    @Then("Product title, description, price, images should be displayed correctly")
    public void validate_product_details() {
        productPage.validateProductDetails();
    }

    @When("User clicks on {string}")
    public void user_clicks_on_button(String button) {
        if (button.equalsIgnoreCase("Add to Wishlist")) {
            productPage.addToWishlist();
        } else if (button.equalsIgnoreCase("Add to Cart")) {
            productPage.addToCart();
        }
    }

    @Then("Wishlist success message should be displayed")
    public void verify_wishlist_message() {
        productPage.verifyWishlistSuccessMessage();
    }

    @Then("The product should be added to the cart")
    public void verify_cart() {
        driver.get("https://demo.opencart.com.gr/index.php?route=checkout/cart");
        driver.navigate().refresh();
        
        // Get the expected product name from the Product Details Page
        String expectedProductName = productPage.getProductTitle();

        // Check if the expected product name exists in the cart page's source
        String pageSource = driver.getPageSource();

        Assert.assertTrue(pageSource.contains(expectedProductName), 
                          "Product not found in cart page source!");
    }
}

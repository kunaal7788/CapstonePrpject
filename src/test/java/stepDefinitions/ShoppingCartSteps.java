package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.ShoppingCartPage;
import java.time.Duration;

public class ShoppingCartSteps {
    private WebDriver driver;
    private WebDriverWait wait;
    private ShoppingCartPage shoppingCartPage;

    /** 
     * ✅ Setup WebDriver before each scenario 
     */
    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));  // Explicit wait
    }

    /** 
     * ✅ Step 1: Open Home Page 
     */
    @Given("User is on the home page to add items")
    public void user_is_on_the_home_page() {
        driver.get("https://demo.opencart.com.gr");
        shoppingCartPage = new ShoppingCartPage(driver);
    }

    /** 
     * ✅ Step 2: Add Product to Cart 
     */
    @When("User adds a product to the cart")
    public void user_adds_a_product_to_the_cart() {
        shoppingCartPage.addProductToCart();
        shoppingCartPage.openCart();
        Assert.assertTrue(shoppingCartPage.isProductInCart(), "Product was not added to cart!");
    }

    /** 
     * ✅ Step 3: Verify Product in Cart 
     */
    @Then("Product should be added to the cart")
    public void product_should_be_added_to_the_cart() {
        Assert.assertTrue(shoppingCartPage.isProductInCart(), "Product not added to cart!");
    }

    /** 
     * ✅ Close WebDriver after each scenario 
     */
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

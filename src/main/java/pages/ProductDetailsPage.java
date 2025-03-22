package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;

public class ProductDetailsPage {
    WebDriver driver;

    // Locators
    private By productTitle = By.xpath("//h1");
    private By productDescription = By.xpath("//*[@id='tab-description']/div");
    private By productPrice = By.xpath("//*[@id='content']/div/div[2]/ul[2]");
    private By productImage = By.xpath("//*[@id='content']/div/div[1]/ul[1]");
    private By addToCartButton = By.xpath("//*[@id='button-cart']");
    private By addToWishlistButton = By.xpath("//button[@data-original-title='Add to Wish List']");
    private By wishlistSuccessMessage = By.xpath("//div[contains(@class, 'alert-success')]");

    public ProductDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getProductTitle() {
        return driver.findElement(productTitle).getText();
    }

    public String getProductDescription() {
        return driver.findElement(productDescription).getText();
    }

    public String getProductPrice() {
        return driver.findElement(productPrice).getText();
    }

    public boolean isProductImageDisplayed() {
        return driver.findElement(productImage).isDisplayed();
    }

    public void validateProductDetails() {
        Assert.assertFalse(getProductTitle().isEmpty(), "Product title is missing");
        Assert.assertFalse(getProductDescription().isEmpty(), "Product description is missing");
        Assert.assertFalse(getProductPrice().isEmpty(), "Product price is missing");
        Assert.assertTrue(isProductImageDisplayed(), "Product image is missing");
    }

    public void addToWishlist() {
        driver.findElement(addToWishlistButton).click();
    }

    public void verifyWishlistSuccessMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement successMessage = wait.until(
            ExpectedConditions.visibilityOfElementLocated(wishlistSuccessMessage)
        );
        Assert.assertTrue(successMessage.isDisplayed(), "Wishlist success message is NOT displayed!");
    }

    public void addToCart() {
        driver.findElement(addToCartButton).click();
    }
}

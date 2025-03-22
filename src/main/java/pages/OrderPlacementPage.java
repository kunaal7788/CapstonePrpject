package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;

public class OrderPlacementPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
   
    private By estimateShippingSection = By.xpath("//*[@id=\"accordion\"]/div[2]/div[1]/h4/a");
    
    private By shippingCountryDropdown = By.id("input-country");
    private By shippingRegionDropdown = By.id("input-zone");
    private By shippingPostalCodeField = By.id("input-postcode");
    private By getQuotesButton = By.id("button-quote");

    private By firstNameField = By.xpath("//*[@id=\"input-payment-firstname\"]");
    private By lastNameField = By.xpath("//*[@id=\"input-payment-lastname\"]");
    private By addressField = By.xpath("//*[@id=\"input-payment-address-1\"]");
    private By cityField = By.xpath("//*[@id=\"input-payment-city\"]");
    private By postalCodeField = By.xpath("//*[@id=\"input-payment-postcode\"]");
    private By countryDropdown = By.xpath("//*[@id=\"input-payment-country\"]");
    private By regionDropdown = By.xpath("//*[@id=\"input-payment-zone\"]");
    private By continueButton = By.xpath("//*[@id=\"button-payment-address\"]");

    private By paymentMethodRadio = By.xpath("//input[@name='payment_method' and @value='cod']");
    private By paymentContinueButton = By.id("button-payment-method");

    private By agreeCheckbox = By.name("agree");
    private By confirmOrderButton = By.id("button-confirm");
    private By orderSuccessMessage = By.xpath("//h1[contains(text(),'Your order has been placed!')]");

    // Constructor
    public OrderPlacementPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Enter shipping details and get quotes
    public void enterShippingDetailsAndGetQuotes(String country, String region, String postalCode) {
        driver.get("https://demo.opencart.com.gr/index.php?route=checkout/cart");
        driver.navigate().refresh();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

        // Click to open estimate shipping section
        wait.until(ExpectedConditions.presenceOfElementLocated(estimateShippingSection)).click();

        // Select country and region
        wait.until(ExpectedConditions.visibilityOfElementLocated(shippingCountryDropdown));
        new Select(driver.findElement(shippingCountryDropdown)).selectByVisibleText(country);
        new Select(driver.findElement(shippingRegionDropdown)).selectByVisibleText(region);

        // Enter postal code
        WebElement postalCodeElement = driver.findElement(shippingPostalCodeField);
        postalCodeElement.clear();
        postalCodeElement.sendKeys(postalCode);

        // Click Get Quotes button
        wait.until(ExpectedConditions.elementToBeClickable(getQuotesButton)).click();

        // **Handle Shipping Modal**
        By shippingModal = By.className("modal-content");
        By shippingRadioButton = By.xpath("//input[@name='shipping_method' and @value='flat.flat']");
        By applyShippingButton = By.id("button-shipping");

        // Wait for modal to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(shippingModal));

        // Select the shipping method radio button
        WebElement radioButton = wait.until(ExpectedConditions.elementToBeClickable(shippingRadioButton));
        radioButton.click();

        // Wait for Apply Shipping button to be enabled and click it
        WebElement applyButton = wait.until(ExpectedConditions.elementToBeClickable(applyShippingButton));
        applyButton.click();
    }

    // Navigate to checkout
    public void proceedToCheckout() {
        By checkoutBtnLocator = By.xpath("//*[@id='content']/div[3]/div[2]/a");
        By modalOverlay = By.className("modal-content"); // The shipping modal
        By checkoutOptionsDropdown = By.xpath("//a[@href='#collapse-checkout-option']");
        By guestCheckoutRadio = By.xpath("//input[@name='account' and @value='guest']");
        By continueGuestButton = By.xpath("//*[@id='button-account']"); // Continue button after selecting Guest Checkout

        try {
            // Wait for modal to disappear if it's still present
            wait.until(ExpectedConditions.invisibilityOfElementLocated(modalOverlay));

            // Wait for checkout button to be Clickable
            WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(checkoutBtnLocator));
            checkoutBtn.click();

            // Check if "Checkout Options" DropDown is displayed
            List<WebElement> checkoutOptions = driver.findElements(checkoutOptionsDropdown);
            if (!checkoutOptions.isEmpty() && checkoutOptions.get(0).isDisplayed()) {
                System.out.println("Checkout Options dropdown is visible, selecting Guest Checkout...");
                wait.until(ExpectedConditions.elementToBeClickable(guestCheckoutRadio)).click();
                wait.until(ExpectedConditions.elementToBeClickable(continueGuestButton)).click();
            }

        } catch (ElementClickInterceptedException e) {
            System.out.println("Retrying click after handling modal...");
            WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(checkoutBtnLocator));
            checkoutBtn.click();
        }
    }

    public void enterBillingDetails(String firstName, String lastName, String address, String city, String postalCode, String country, String region) {
        Actions actions = new Actions(driver);

        WebElement firstNameElement = driver.findElement(firstNameField);
        WebElement lastNameElement = driver.findElement(lastNameField);
        WebElement addressElement = driver.findElement(addressField);
        WebElement cityElement = driver.findElement(cityField);
        WebElement postalCodeElement = driver.findElement(postalCodeField);
        WebElement countryElement = driver.findElement(countryDropdown);
        WebElement regionElement = driver.findElement(regionDropdown);
        WebElement continueBtn = driver.findElement(continueButton);

        // Slow typing simulation
        typeSlowly(actions, firstNameElement, firstName);
        typeSlowly(actions, lastNameElement, lastName);
        typeSlowly(actions, addressElement, address);
        typeSlowly(actions, cityElement, city);
        typeSlowly(actions, postalCodeElement, postalCode);

        // Selecting DropDown values
        new Select(countryElement).selectByVisibleText(country);
        new Select(regionElement).selectByVisibleText(region);

        // Click continue button
        actions.moveToElement(continueBtn).click().perform();
    }

    // Helper method to type text slowly using Actions class
    private void typeSlowly(Actions actions, WebElement element, String text) {
        element.clear();  // Clear the field before entering text
        for (char c : text.toCharArray()) {
            actions.sendKeys(element, String.valueOf(c)).pause(200).perform(); 
        }
    }


    // Select Delivery Details (Existing Address)
    public void selectDeliveryDetails() {
        By existingAddressRadio = By.xpath("//*[@id=\"collapse-shipping-address\"]/div/form/div[1]");
        By shippingAddressContinue = By.xpath("//*[@id=\"button-shipping-address\"]");

        WebElement radioBtn = wait.until(ExpectedConditions.elementToBeClickable(existingAddressRadio));
        
        // Ensure it's selected (sometimes it may be pre-selected)
        if (!radioBtn.isSelected()) {
            radioBtn.click();
        }

        // Scroll into view and click Continue using JavaScriptExecutor
        WebElement continueButton = wait.until(ExpectedConditions.visibilityOfElementLocated(shippingAddressContinue));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", continueButton);
    }


    // Select Delivery Method
    public void selectDeliveryMethod() throws InterruptedException {
        By deliveryMethodDropdown = By.xpath("//*[@id=\"accordion\"]/div[4]/div[1]");
        By deliveryMethodRadio = By.xpath("//*[@id=\"collapse-shipping-method\"]/div/div[1]/label/input");
        By shippingMethodContinue = By.xpath("//*[@id=\"button-shipping-method\"]");

        // Expand delivery method dropdown
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(deliveryMethodDropdown));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropdown);
        Thread.sleep(500); // Allow UI update
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);

        // Ensure radio button is selected
        WebElement radioBtn = wait.until(ExpectedConditions.elementToBeClickable(deliveryMethodRadio));
        if (!radioBtn.isSelected()) {
            try {
                radioBtn.click(); // Normal click
            } catch (Exception e) {
                System.out.println("Regular click on radio button failed, using JavaScript...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", radioBtn);
            }
        }

        // Small wait to allow UI updates
        Thread.sleep(1000);

        // Scroll into view and click Continue button
        WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(shippingMethodContinue));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", continueButton);
        Thread.sleep(500); 

        try {
            continueButton.click(); 
        } catch (ElementClickInterceptedException e) {
            System.out.println("Continue button click intercepted, retrying...");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", continueButton);
        }

        // Small wait to ensure the UI updates properly
        Thread.sleep(1000);
    }

    // Select Payment Method
    public void selectPaymentMethod() throws InterruptedException {
    	Thread.sleep(3000);
        WebElement paymentRadio = wait.until(ExpectedConditions.elementToBeClickable(paymentMethodRadio));
        if (!paymentRadio.isSelected()) {
            paymentRadio.click();
        }
        WebElement agree = wait.until(ExpectedConditions.elementToBeClickable(agreeCheckbox));
        if (!agree.isSelected()) {
            agree.click();
        }


        WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(paymentContinueButton));
        
        try {
            continueButton.click();
        } catch (Exception e) {
            System.out.println("Regular click failed, trying JavaScript click...");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", continueButton);
        }
    }


    // Confirm order
    public void confirmOrder() {
        WebElement agree = wait.until(ExpectedConditions.elementToBeClickable(agreeCheckbox));
        if (!agree.isSelected()) {
            agree.click();
        }

        WebElement confirmButton = wait.until(ExpectedConditions.presenceOfElementLocated(confirmOrderButton));
        wait.until(ExpectedConditions.visibilityOf(confirmButton));
        wait.until(ExpectedConditions.elementToBeClickable(confirmButton));

        try {
            confirmButton.click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("Element not clickable, using JavaScript click...");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", confirmButton);
        }
    }

    // Validate order confirmation
    public boolean isOrderPlaced() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessMessage)).isDisplayed();
    }
}

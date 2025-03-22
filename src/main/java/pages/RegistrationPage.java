package pages;

import java.time.Duration;
//import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage {
	private WebDriver driver;
	private WebDriverWait wait;


	private By myAccount = By.xpath("//*[@id=\"top-links\"]/ul/li[2]/a/span[1]");
	private By registerLink = By.linkText("Register");
	private By firstNameField = By.id("input-firstname");
	private By lastNameField =By.id("input-lastname");
	private By emailField = By.id("input-email");
	private By telephoneField = By.id("input-telephone");
	private By passwordField = By.id("input-password");
	private By confirmPasswordField = By.id("input-confirm");
	private By subscribeYes = By.xpath("//*[@id='content']/form/fieldset[3]/div/div/label[1]/input");
	private By subscribeNo = By.xpath("//*[@id='content']/form/fieldset[3]/div/div/label[2]/input");
	private By privacyPolicyCheckbox = By.xpath("//*[@id=\"content\"]/form/div/div/input[1]");
	private By registerButton = By.xpath("//*[@id=\"content\"]/form/div/div/input[2]");
	private By successMessage = By.xpath("//*[@id=\"content\"]/p[1]");

	// Constructor
	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		driver.manage().deleteAllCookies();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	public void navigateToRegisterPage() {
		wait.until(ExpectedConditions.elementToBeClickable(myAccount)).click();
		wait.until(ExpectedConditions.elementToBeClickable(registerLink)).click();

		// Validate if the URL contains "route=account/register"
		//        wait.until(ExpectedConditions.urlContains("route=account/register"));
	}


	public void registerUser(String firstName, String lastName, String email, String telephone, String password, String subscribe) {
		Actions actions = new Actions(driver);

		typeWithDelay(actions, driver.findElement(firstNameField), firstName);
		typeWithDelay(actions, driver.findElement(lastNameField), lastName);
		typeWithDelay(actions, driver.findElement(emailField), email);
		typeWithDelay(actions, driver.findElement(telephoneField), telephone);
		typeWithDelay(actions, driver.findElement(passwordField), password);
		typeWithDelay(actions, driver.findElement(confirmPasswordField), password);

		if (subscribe.equalsIgnoreCase("Yes")) {
			driver.findElement(subscribeYes).click();
		} else {
			driver.findElement(subscribeNo).click();
		}

		WebElement checkbox = driver.findElement(privacyPolicyCheckbox);
		if (!checkbox.isSelected()) {
			checkbox.click();
		}

		driver.findElement(registerButton).click();
	}

	private void typeWithDelay(Actions actions, WebElement element, String text) {
		for (char c : text.toCharArray()) {
			actions.sendKeys(element, String.valueOf(c)).pause(Duration.ofMillis(300)).perform();
		}
	}

	public boolean isRegistrationSuccessAndLogout() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    driver.manage().deleteAllCookies();

	    try {
	        // Wait for success message
	        WebElement successMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
	        String successText = successMessageElement.getText();

	        // Verify success message
	        if (!successText.contains("Congratulations!")) {
	            return false;
	        }

	        // Click Continue Button to move forward
	        By continueButton = By.xpath("//*[@id='content']/div/div/a");
	        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();

	        // Wait briefly to allow the page transition
	        Thread.sleep(2000);

	        // Check if reload button appears due to redirection issue
//	        By reloadButton = By.xpath("//*[@id='reload-button']");
//	        List<WebElement> reloadButtonElements = driver.findElements(reloadButton);
//	        if (!reloadButtonElements.isEmpty()) {
//	            System.out.println("Reload button detected. Clicking to refresh the page...");
//	            reloadButtonElements.get(0).click();
//	            wait.until(ExpectedConditions.stalenessOf(reloadButtonElements.get(0))); // Wait for page to refresh
//	        }
	     // Handle potential redirection error (if page gets stuck)
//	        if (driver.getTitle().contains("Too Many Redirects")) {
//	            System.out.println("Detected redirect loop. Refreshing page...");
//	            driver.findElement(reloadButton).click(); 
//	            wait.until(ExpectedConditions.urlContains("route=account/logout"));
//	        }

	        // Logout process
	        By logoutLink = By.xpath("//*[@id='column-right']/div/a[13]");
	        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();

	        // Ensure redirection to logout success page
	      //  wait.until(ExpectedConditions.urlContains("route=account/logout"));

	        // Click Login button
	        By loginButton = By.xpath("//*[@id='column-right']/div/a[1]");
	        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();

	        // Ensure navigation to login page
	        return wait.until(ExpectedConditions.urlContains("route=account/login"));

	    } catch (Exception e) {
	        System.out.println("Error during registration logout process: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
	}

}

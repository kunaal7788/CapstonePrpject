
package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class LoginPage {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    //Locators for login page elements
    private By emailField = By.id("input-email");
    private By passwordField = By.xpath("//*[@id=\"input-password\"]");
    private By loginButton = By.xpath("//*[@id=\"content\"]/div/div[2]/div/form/input[1]");
    private By logoutButton = By.xpath("//*[@id=\"column-right\"]/div/a[13]");
    private By errorMessage = By.xpath("//div[contains(@class,'alert-danger')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        driver.manage().deleteAllCookies();
        this.setWait(new WebDriverWait(driver, Duration.ofSeconds(15)));
    }

    public void login(String email, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);

        try {
            WebElement emailElement = wait.until(ExpectedConditions.elementToBeClickable(emailField));
            WebElement passwordElement = wait.until(ExpectedConditions.elementToBeClickable(passwordField));
            WebElement loginBtnElement = wait.until(ExpectedConditions.elementToBeClickable(loginButton));

            // Slow typing effect
            typeWithDelay(actions, emailElement, email);
            typeWithDelay(actions, passwordElement, password);

            loginBtnElement.click();
            System.out.println("Login attempt successful with provided credentials.");
        } catch (TimeoutException e) {
            System.out.println("Login attempt failed: One or more elements did not appear within the timeout.");
        }
    }

    // Reusable method for slow typing
    private void typeWithDelay(Actions actions, WebElement element, String text) {
        for (char c : text.toCharArray()) {
            actions.sendKeys(element, String.valueOf(c)).pause(Duration.ofMillis(300)).perform();
        }
    }


    public boolean isLoginSuccessful() {
        boolean isSuccessful = !driver.findElements(logoutButton).isEmpty();

        if (isSuccessful) {
            System.out.println("Login Successful: Logout button is visible.");
        } else {
            System.out.println("Login Failed: Logout button not found.");
        }

        return isSuccessful;
    }

    public boolean isLoginFailed() {
        boolean isFailed = !driver.findElements(errorMessage).isEmpty();

        if (isFailed) {
            System.out.println("Login Failed: Error message is displayed.");
        } else {
            System.out.println("Login Success: No error message found.");
        }

        return isFailed;
    }


	public WebDriverWait getWait() {
		return wait;
	}


	public void setWait(WebDriverWait wait) {
		this.wait = wait;
	}
}



package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class HomePage {
    WebDriver driver;

    // Locators for home page elements
    private By recommendedSection = By.xpath("//*[@id=\"content\"]/div[2]");
    private By searchBar = By.xpath("//*[@id=\"search\"]/input");
    
   
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

  
    public boolean isTopOffersVisible() {
    	//Explicitly wait for the element to be visible
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content > div:nth-child(1)")));
       
        boolean isVisible = element.isDisplayed();
        
        if (isVisible) {
            System.out.println("Top Offers Check Success: Element is visible.");
        } else {
            System.out.println("Top Offers Check Failed: Element is not visible.");
        }
        
        return isVisible;
    }

    public boolean isRecommendedVisible() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(recommendedSection));

            boolean isVisible = element.isDisplayed() && element.getSize().getHeight() > 0;

            if (isVisible) {
                System.out.println("Recommended Section Check Success: Element is visible.");
            } else {
                System.out.println("Recommended Section Check Failed: Element is not visible.");
            }

            return isVisible;
        } catch (TimeoutException e) {
            System.out.println("Recommended Section Check Failed: Element did not appear within the timeout.");
            return false;
        }
    }

    public boolean isSearchBarVisible() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement searchBarElement = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBar));

            System.out.println("Search Bar Check Success: Element is visible.");
            return searchBarElement.isDisplayed();
        } catch (TimeoutException e) {
            System.out.println("Search Bar Check Failed: Element is not visible within the timeout.");
            return false;
        }
    }


}

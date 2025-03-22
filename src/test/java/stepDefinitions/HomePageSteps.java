package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.HomePage;
import utilities.DriverFactory;

public class HomePageSteps {
    WebDriver driver = DriverFactory.getDriver();
    HomePage homePage = new HomePage(driver);
    
    @Given("I open the homepage")
    public void i_open_the_homepage() {
        driver.get("https://demo.opencart.com.gr");  
        
    }

    @Then("I should see the Top Offers section")
    public void verify_top_offers_section() {
    	
        Assert.assertTrue(homePage.isTopOffersVisible(), "Top Offers section is missing!");
    }

    @Then("I should see the Recommended for You section")
    public void verify_recommended_section() {
        Assert.assertTrue(homePage.isRecommendedVisible(), "Recommended for You section is missing!");
    }

    @Then("The search bar should be visible")
    public void verify_search_bar() {
        Assert.assertTrue(homePage.isSearchBarVisible(), "Search bar is missing!");
    }
}

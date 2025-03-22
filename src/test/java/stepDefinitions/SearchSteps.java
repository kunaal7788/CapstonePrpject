package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import pages.SearchPage;
import utilities.DriverFactory;

public class SearchSteps {
    WebDriver driver = DriverFactory.getDriver();
    SearchPage searchPage = new SearchPage(driver);
    @Test(priority = 3)
    @Given("I am on the homepage")
    public void i_am_on_the_homepage() {
        driver.get("https://demo.opencart.com.gr/index.php?route=common/home");
    }

    @When("I Search for {string}") // Handles 'Search' with capital 'S'
    @When("I search for {string}") // Handles 'search' with lowercase 's'
    public void i_search_for(String product) {
        searchPage.searchProduct(product);
    }

    @Then("I should see search results related to {string}")
    public void i_should_see_search_results_related_to(String expectedProduct) {
        Assert.assertTrue(searchPage.isSearchResultDisplayed(expectedProduct),
                "Search results do not contain expected product: " + expectedProduct);
    }

    @Given("I am on the search page")
    public void i_am_on_the_search_page() {
        driver.get("https://demo.opencart.com.gr/index.php?route=product/search");
    }

    @When("I filter product by category {string}")
    public void i_filter_product_by_category(String category) {
        searchPage.filterByCategory(category);
    }

    @Then("I should see only {string} results")
    public void i_should_see_only_results(String expectedCategory) {
        Assert.assertTrue(searchPage.isFilteredByCategory(),
                "Results are not filtered properly for category: " + expectedCategory);
    }

    @When("I sort results by {string}")
    public void i_sort_results_by(String sortOption) {
        searchPage.sortResultsBy(sortOption);
    }

    @Then("I should see products sorted from lowest to highest price")
    public void i_should_see_products_sorted_from_lowest_to_highest_price() {
        Assert.assertTrue(searchPage.isSortedByPriceLowToHigh(), "Products are not sorted from low to high price.");
    }

    @Then("I should see products sorted from highest to lowest rating")
    public void i_should_see_products_sorted_from_highest_to_lowest_rating() {
        Assert.assertTrue(searchPage.isSortedByRatingHighToLow(),
                "Products are not sorted from highest to lowest rating.");
    }

    @Then("I should see products sorted from lowest to highest rating")
    public void i_should_see_products_sorted_from_lowest_to_highest_rating() {
        Assert.assertTrue(searchPage.isSortedByRatingLowToHigh(),
                "Products are not sorted from lowest to highest rating.");
    }
}  
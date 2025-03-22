package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import java.time.Duration;

public class SearchPage {
    WebDriver driver;
    WebDriverWait wait;

    // Locators
    private By searchBar = By.name("search");
    private By searchBar2 = By.xpath("//*[@id='input-search']");
    private By searchButton = By.xpath("//button[@class='btn btn-default btn-lg']");
    private By searchButton2 = By.xpath("//*[@id='button-search']");
    private By searchCheckbox = By.xpath("//*[@id='content']/p/label"); 
    private By searchResults = By.cssSelector(".product-thumb h4 a");
    private By categoryDropdown = By.xpath("//*[@id=\"content\"]/div[1]/div[2]/select");
    private By sortDropdown = By.id("input-sort");
    private By productPrice = By.cssSelector(".product-thumb .price");
    private By productRating = By.cssSelector(".product-thumb .rating"); // Rating locator

    // Constructor
    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Perform search operation
    public void searchProduct(String product) {
        WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(searchBar));
        searchInput.clear();
        searchInput.sendKeys(product);
      
        driver.findElement(searchButton).click();
        
        WebElement searchInput2 = wait.until(ExpectedConditions.elementToBeClickable(searchBar2));
        searchInput2.clear();
        searchInput2.sendKeys(product);
        
        WebElement checkbox = driver.findElement(searchCheckbox);
        if (!checkbox.isSelected()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
        }

        driver.findElement(searchButton2).click();
    }
    //Verify if the search results are displayed or not
    public boolean isSearchResultDisplayed(String expectedProduct) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait until search results are visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchResults));

        List<WebElement> results = driver.findElements(searchResults);
        
        for (WebElement result : results) {
            if (result.getText().contains(expectedProduct)) {
                System.out.println("Search Result Check Success: Found expected product - " + expectedProduct);
                return true;
            }
        }

        System.out.println("Search Result Check Failed: Expected product not found - " + expectedProduct);
        return false;
    }

    // Select category from DropDown
    public void filterByCategory(String category) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait until the category DropDown is visible
        WebElement dropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(categoryDropdown));

        Select categorySelect = new Select(dropdownElement);
        categorySelect.selectByVisibleText(category);

        System.out.println("Category Filter Success: Selected category - " + category);
    }


    // Check if results are filtered
    public boolean isFilteredByCategory() {
        List<WebElement> results = driver.findElements(searchResults);
        return results.size() > 0;
    }

    // Sort search results by given option
    public void sortResultsBy(String option) {
        Select sortSelect = new Select(driver.findElement(sortDropdown));
        sortSelect.selectByVisibleText(option);
    }

    // Verify sorting by price (low to high)
    public boolean isSortedByPriceLowToHigh() {
        List<WebElement> prices = driver.findElements(productPrice);
        return isSortedAscending(prices);
    }
    

    // Verify sorting by rating (high to low)
    public boolean isSortedByRatingHighToLow() {
        List<WebElement> ratings = driver.findElements(productRating);
        return isSortedDescending(ratings);
    }

    // Verify sorting by rating (low to high)
    public boolean isSortedByRatingLowToHigh() {
        List<WebElement> ratings = driver.findElements(productRating);
        return isSortedAscending(ratings);
    }

    // Sorting verification helper methods
    private boolean isSortedAscending(List<WebElement> elements) {
        double prevValue = 0.0;
        for (WebElement element : elements) {
            double currentValue = extractNumericValue(element.getText());
            if (currentValue < prevValue) return false;
            prevValue = currentValue;
        }
        return true;
    }

    private boolean isSortedDescending(List<WebElement> elements) {
        double prevValue = Double.MAX_VALUE;
        for (WebElement element : elements) {
            double currentValue = extractNumericValue(element.getText());
            if (currentValue > prevValue) return false;
            prevValue = currentValue;
        }
        return true;
    }

    // Extract numeric value from text (price or rating)
    private double extractNumericValue(String text) {
        String numericText = text.replaceAll("[^0-9.]", "");
        return numericText.isEmpty() ? 0.0 : Double.parseDouble(numericText);
    }
}

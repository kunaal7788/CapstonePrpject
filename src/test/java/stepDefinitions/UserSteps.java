
package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.LoginPage;
import pages.RegistrationPage;
import utilities.DriverFactory;
import utilities.ExcelUtils;
import java.io.IOException;

public class UserSteps {
    WebDriver driver;
    RegistrationPage registrationPage;
    LoginPage loginPage;
    private static final String EXCEL_PATH = "src/test/resources/data/TestData.xlsx";
    private static final String SHEET_NAME = "Sheet1";

    public UserSteps() {
        this.driver = DriverFactory.getDriver();
        this.registrationPage = new RegistrationPage(driver);
        this.loginPage = new LoginPage(driver);
    }
    
    @Given("User is on registration page")
    public void user_is_on_registration_page() {
        driver.get("https://demo.opencart.com.gr/index.php?route=account/register");
    }

    @When("User enters valid registration details from Excel")
    public void user_enters_valid_registration_details_from_excel() throws InterruptedException {
        try {
            ExcelUtils.loadExcel(EXCEL_PATH, SHEET_NAME);

            String firstName = ExcelUtils.getCellData(1, 1);
            String lastName = ExcelUtils.getCellData(1, 2);
            String email = ExcelUtils.getCellData(1, 3);
            String telephone = ExcelUtils.getCellData(1, 4);
            String password = ExcelUtils.getCellData(1, 5);
            String subscribe = ExcelUtils.getCellData(1, 6);

            registrationPage.registerUser(firstName, lastName, email, telephone, password, subscribe);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Failed to read data from Excel");
        } finally {
            try {
                ExcelUtils.closeExcel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Then("Registration should be successful")
    public void registration_should_be_successful() {
        boolean actualStatus = registrationPage.isRegistrationSuccessAndLogout();
        
        System.out.println("Actual Registration Status: " + actualStatus);
        
        try {
            Assert.assertTrue(actualStatus, "Registration Failed!");
        } catch (AssertionError e) {
            System.out.println("Test Failed: " + e.getMessage());
            e.printStackTrace(); 
            throw e;
        }
    }


    @Given("User is on login page")
    public void user_is_on_login_page() {
        driver.get("https://demo.opencart.com.gr/index.php?route=account/login");
    }
    @When("User enters invalid login credentials from Excel")
    public void user_enters_invalid_login_credentials_from_excel() {
        try {
            ExcelUtils.loadExcel(EXCEL_PATH, SHEET_NAME);

            String email = ExcelUtils.getCellData(2, 3);
            String password = ExcelUtils.getCellData(2, 5);

            loginPage.login(email, password);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Failed to read data from Excel");
        } finally {
            try {
                ExcelUtils.closeExcel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Then("Login should fail")
    public void login_should_fail() {
        Assert.assertTrue(loginPage.isLoginFailed(), "Login Passed with Invalid Data!");
    }

    @When("User enters valid login credentials from Excel")
    public void user_enters_valid_login_credentials_from_excel() {
        try {
            ExcelUtils.loadExcel(EXCEL_PATH, SHEET_NAME);

            String email = ExcelUtils.getCellData(1, 3);
            String password = ExcelUtils.getCellData(1, 5);

            loginPage.login(email, password);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Failed to read data from Excel");
        } finally {
            try {
                ExcelUtils.closeExcel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Then("User should be logged in successfully")
    public void user_should_be_logged_in_successfully() {
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login Failed!");
    }
 }
    
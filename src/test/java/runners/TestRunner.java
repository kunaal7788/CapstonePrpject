package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import utilities.DriverFactory;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

@CucumberOptions(
        features = "src/test/resources/features/",
        glue = "stepDefinitions",
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        monochrome = true 
        
)
public class TestRunner extends AbstractTestNGCucumberTests {
    @BeforeClass
    public static void setup() {
        DriverFactory.getDriver();
    }

    @AfterClass
    public static void teardown() {
        DriverFactory.quitDriver();
    }
}

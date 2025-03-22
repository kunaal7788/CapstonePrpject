package Listeners;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.WebDriver;
import utilities.ScreenshotUtil;

public class TestNGListener implements ITestListener {
    private WebDriver driver;

    @Override
    public void onTestFailure(ITestResult result) {
        Object testInstance = result.getInstance();
        try {
            driver = (WebDriver) testInstance.getClass().getDeclaredField("driver").get(testInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (driver != null) {
            String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName());
            System.out.println("Screenshot taken: " + screenshotPath);
        }
    }
}

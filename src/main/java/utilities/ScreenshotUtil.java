package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {
    public static String captureScreenshot(WebDriver driver, String testName) {
        try {
            // Generate unique TimeStamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotPath = "screenshots/" + testName + "_" + timestamp + ".png";

            // Take Screenshot
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(screenshotPath);

            // Save File
            FileUtils.copyFile(srcFile, destFile);
            return screenshotPath;

        } catch (IOException e) {
            System.out.println("Error while capturing screenshot: " + e.getMessage());
            return null;
        }
    }
}

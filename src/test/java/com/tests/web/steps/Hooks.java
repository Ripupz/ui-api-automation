package com.tests.web.steps;

import com.tests.web.utils.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Hooks {

    @After("@web") // Runs after every scenario tagged with @web
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverFactory.getDriver();

        if (driver != null) {
            // OPTIONAL: Take a screenshot if the test failed
            if (scenario.isFailed()) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Failed Screenshot");
            }

            // CRITICAL: Close the browser
            driver.quit();

            // CRITICAL: Reset the driver in your Factory to null
            // This ensures the next test gets a fresh browser
            DriverFactory.quitDriver();
        }
    }
}
package com.tests.web.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driver.get() == null) {

            WebDriverManager.firefoxdriver().clearDriverCache().setup();

            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");          // headless mode
            options.addArguments("--width=1280");
            options.addArguments("--height=720");

            driver.set(new FirefoxDriver(options));
        }

        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}

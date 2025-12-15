package com.tests.web.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
// import firefox if you prefer

public class DriverFactory {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            String browser = System.getenv().getOrDefault("BROWSER", "chrome").toLowerCase();
            if (browser.equals("chrome")) {
                WebDriverManager.chromedriver().clearDriverCache().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--window-size=1280,720");
                options.addArguments("--remote-allow-origins=*");
                driver.set(new ChromeDriver(options));
            } else {
                // implement firefox if required
                WebDriverManager.firefoxdriver().setup();
                // ...
            }
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

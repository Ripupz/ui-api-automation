package com.tests.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    String homeId = "nava";
    @FindBy()
    private WebElement navBar; // demo blase store nav id

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open(String url) {
        driver.get(url);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public void goToHomePage() {
        WebElement homeElement = driver.findElement(By.id(homeId));
        homeElement.click();
    }
    public void addFirstProductToCart() {
        // simple approach: click first product, add to cart, accept alert
        WebElement firstProduct = driver.findElement(By.cssSelector(".card-block .hrefch"));
        firstProduct.click();
        WebElement addBtn = driver.findElement(By.cssSelector(".btn-success"));
        addBtn.click();
        driver.switchTo().alert().accept();
    }

    public int getCartCount() {
        // DemoBlaze doesn't show count in header by default; adapt as needed.
        // this is simplistic: open cart page and count items
        driver.findElement(By.id("cartur")).click();
        return driver.findElements(By.cssSelector(".success")).size();
    }
}

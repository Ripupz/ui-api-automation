package com.tests.web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    // Define elements using @FindBy
    @FindBy(id = "nava")
    private WebElement homeLogo;

    @FindBy(id = "cartur")
    private WebElement cartLink;

    @FindBy(css = "a.hrefch")
    private WebElement firstProductLink;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("https://www.demoblaze.com/");
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public void clickHomeLogo() {
        click(homeLogo);
    }

    public void clickFirstProduct() {
        click(firstProductLink);
    }

    public void clickCart() {
        click(cartLink);
    }
}
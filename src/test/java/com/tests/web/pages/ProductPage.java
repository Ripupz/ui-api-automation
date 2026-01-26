package com.tests.web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BasePage {

    @FindBy(xpath = "//a[contains(text(),'Add to cart')]")
    private WebElement addToCartBtn;

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAddToCartButtonVisible() {
        return isDisplayed(addToCartBtn);
    }

    public void clickAddToCart() {
        click(addToCartBtn);
    }
}
package com.tests.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class CartPage extends BasePage {

    // --- Elements ---
    @FindBy(xpath = "//button[text()='Place Order']")
    private WebElement placeOrderBtn;

    @FindBy(xpath = "//tbody[@id='tbodyid']//a[contains(text(),'Delete')]")
    private WebElement deleteLink;

    @FindBy(id = "orderModal")
    private WebElement orderModal;

    // Modal Inputs
    @FindBy(id = "name") private WebElement nameInput;
    @FindBy(id = "country") private WebElement countryInput;
    @FindBy(id = "city") private WebElement cityInput;
    @FindBy(id = "card") private WebElement cardInput;
    @FindBy(id = "month") private WebElement monthInput;
    @FindBy(id = "year") private WebElement yearInput;

    @FindBy(xpath = "//button[text()='Purchase']")
    private WebElement purchaseBtn;

    @FindBy(xpath = "//h2[contains(text(),'Thank you for your purchase!')]")
    private WebElement successMessage;

    @FindBy(xpath = "//button[text()='OK']")
    private WebElement okBtn;

    // Need raw Locator for List logic
    private final By tableRows = By.cssSelector("#tbodyid tr.success");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    // --- Methods ---

    public int getCartCount() {
        // Wait for rows to be visible or empty list if none
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(tableRows));
            return driver.findElements(tableRows).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public void deleteFirstItem() {
        click(deleteLink);
        // Wait for the row to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(tableRows));
    }

    public void clickPlaceOrder() {
        click(placeOrderBtn);
        wait.until(ExpectedConditions.visibilityOf(orderModal));
    }

    public void fillOrderForm(String name, String country, String city, String card, String month, String year) {
        sendKeys(nameInput, name);
        sendKeys(countryInput, country);
        sendKeys(cityInput, city);
        sendKeys(cardInput, card);
        sendKeys(monthInput, month);
        sendKeys(yearInput, year);
    }

    public void clickPurchase() {
        click(purchaseBtn);
    }

    public boolean isPurchaseSuccessDisplayed() {
        return isDisplayed(successMessage);
    }

    public void clickOK() {
        click(okBtn);
    }
}
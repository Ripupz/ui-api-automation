package com.tests.web.steps;

import com.tests.web.pages.CartPage;
import com.tests.web.pages.HomePage;
import com.tests.web.pages.ProductPage;
import com.tests.web.utils.DriverFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class WebSteps {

    // 1. Get Driver
    WebDriver driver = DriverFactory.getDriver();

    // 2. Instantiate Page Objects
    HomePage homePage = new HomePage(driver);
    ProductPage productPage = new ProductPage(driver);
    CartPage cartPage = new CartPage(driver);

    // ================= HOMEPAGE STEPS =================

    @Given("I open the homepage")
    public void iOpenTheHomepage() {
        homePage.open();
    }

    @Then("the page title should contain {string}")
    public void thePageTitleShouldContain(String expected) {
        String actualTitle = homePage.getTitle();
        Assert.assertTrue(actualTitle.contains(expected),
                "Title mismatch! Expected to contain: " + expected + ", but got: " + actualTitle);
    }

    @When("I click the first product")
    public void iClickTheFirstProduct() {
        homePage.clickFirstProduct();
    }

    // ================= PRODUCT PAGE STEPS =================

    @Then("the product detail page should be displayed")
    public void theProductDetailPageShouldBeDisplayed() {
        Assert.assertTrue(productPage.isAddToCartButtonVisible(),
                "Add to Cart button is missing! We might not be on the Product Page.");
    }

    @Given("I am at the product detailPage")
    public void iAmAtTheProductDetailPage() {
        homePage.open();
        homePage.clickFirstProduct();
        // Verification is good practice in 'Given' steps to ensure state is correct
        Assert.assertTrue(productPage.isAddToCartButtonVisible());
    }

    @When("I add the first product to cart")
    public void iAddTheFirstProductToCart() {
        productPage.clickAddToCart();
        productPage.acceptAlert();
    }

    @When("I add {int} products to the cart")
    public void iAddProductsToTheCart(int count) {
        for (int i = 0; i < count; i++) {
            // Re-navigating in loop using POM methods
            homePage.clickFirstProduct();
            productPage.clickAddToCart();
            productPage.acceptAlert();
            homePage.open(); // Go back to start of flow
        }
    }

    // ================= CART PAGE STEPS =================

    @Given("I have a product in the cart")
    public void iHaveAProductInTheCart() {
        homePage.open();
        homePage.clickFirstProduct();
        productPage.clickAddToCart();
        productPage.acceptAlert();
    }

    @When("I remove the product from the cart")
    public void iRemoveTheProductFromTheCart() {
        homePage.clickCart();
        cartPage.deleteFirstItem();
    }

    @Then("the cart should contain at least {int} item(s)")
    public void theCartShouldContainAtLeastItems(int expectedCount) {
        homePage.clickCart();
        int actualCount = cartPage.getCartCount();
        Assert.assertTrue(actualCount >= expectedCount,
                "Expected at least " + expectedCount + " item(s), but found: " + actualCount);
    }

    @Then("the cart should be empty")
    public void theCartShouldBeEmpty() {
        int actualCount = cartPage.getCartCount();
        Assert.assertEquals(actualCount, 0, "Cart is not empty!");
    }

    // ================= CHECKOUT STEPS =================

    @When("I proceed to checkout")
    public void iProceedToCheckout() {
        homePage.clickCart();       // Navigate to Cart
        cartPage.clickPlaceOrder(); // Open Modal
    }

    @And("I submit the order without filling the form")
    public void iSubmitTheOrderWithoutFillingTheForm() {
        cartPage.clickPurchase();
    }

    @Then("an error message should be displayed")
    public void anErrorMessageShouldBeDisplayed() {
        // Accessing the Alert logic we put in BasePage
        String errorText = cartPage.getAlertText();
        Assert.assertTrue(errorText.contains("Please fill out Name and Creditcard"),
                "Unexpected error text: " + errorText);

        cartPage.acceptAlert();
    }

    @And("I fill the order form with valid data")
    public void iFillTheOrderFormWithValidData() {
        // Using the helper method we created in CartPage
        cartPage.fillOrderForm("Test User", "USA", "New York", "1234567890", "12", "2025");
    }

    @And("I confirm the purchase")
    public void iConfirmThePurchase() {
        cartPage.clickPurchase();
    }

    @Then("the purchase should be successful")
    public void thePurchaseShouldBeSuccessful() {
        Assert.assertTrue(cartPage.isPurchaseSuccessDisplayed(), "Success 'Thank You' message not displayed!");
        cartPage.clickOK();
    }
}
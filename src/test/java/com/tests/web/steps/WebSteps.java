package com.tests.web.steps;


import com.tests.web.utils.DriverFactory;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;


public class WebSteps {

    WebDriver driver = DriverFactory.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // Verify Homepage
    @Given("I open the homepage")
    public void iOpenTheHomepage() {
        driver.get("https://www.demoblaze.com/");
    }

    @Then("the page title should contain {string}")
    public void thePageTitleShouldContain(String expected) {
        String actual = driver.getTitle();
        // In TestNG: assertTrue(condition, message)
        Assert.assertTrue(
                actual.contains(expected),
                "Expected title to contain: " + expected + " but got: " + actual
        );
    }

    @When("I click the first product")
    public void iClickTheFirstProduct() {
        // Wait for the first product link (Samsung galaxy s6) to be clickable and click it
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.hrefch"))).click();
    }

    @Then("the product detail page should be displayed")
    public void theProductDetailPageShouldBeDisplayed() {
        // Verify the 'Add to cart' button is visible, which confirms we are on a product detail page
        boolean isAddToCartVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(),'Add to cart')]"))).isDisplayed();

        Assert.assertTrue(isAddToCartVisible, "Product detail page was not displayed (Add to cart button missing)");
    }

    @Given("I am at the product detailPage")
    public void iAmAtTheProductDetailPage() {
        // 1. Go to homepage
        iOpenTheHomepage();

        // 2. Click the first product to get to the detail page
        iClickTheFirstProduct();

        // 3. Verify 'Add to cart' is visible to confirm we are on the detail page
        theProductDetailPageShouldBeDisplayed();
    }

    @When("I add the first product to cart")
    public void iAddTheFirstProductToCart() {
        // 1. Click 'Add to cart' button
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Add to cart')]"))).click();

        // 2. Handle the browser alert ("Product added")
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    @Then("the cart should contain at least {int} item")
    public void theCartShouldContainAtLeastItem(int expectedCount) {
        // 1. Navigate to Cart
        wait.until(ExpectedConditions.elementToBeClickable(By.id("cartur"))).click();

        // 2. Wait for the items to be injected into the DOM
        // We wait for the 'success' class rows to appear as seen in your HTML
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#tbodyid tr.success")));

        // 3. Find all rows
        List<WebElement> cartItems = driver.findElements(By.cssSelector("#tbodyid tr"));

        // 4. TestNG Assertion
        Assert.assertTrue(
                cartItems.size() >= expectedCount,
                "Cart count mismatch! Expected at least: " + expectedCount + " but found: " + cartItems.size()
        );
    }


    @When("I add {int} products to the cart")
    public void iAddProductsToTheCart(int count) {
        for (int i = 0; i < count; i++) {
            // 1. Click the first product from the homepage grid
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.hrefch"))).click();

            // 2. Click the 'Add to cart' button on the detail page
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Add to cart')]"))).click();

            // 3. Handle the browser alert
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            alert.accept();

            // 4. Navigate back to the homepage to add the next item
            driver.get("https://www.demoblaze.com/");
        }
    }

    @Then("the cart should contain at least {int} items")
    public void theCartShouldContainAtLeastItems(int expectedCount) {
        // 1. Navigate to the Cart page
        wait.until(ExpectedConditions.elementToBeClickable(By.id("cartur"))).click();

        // 2. WAIT specifically for the number of rows to reach the expected count
        wait.until(driver -> driver.findElements(By.cssSelector("#tbodyid tr.success")).size() >= expectedCount);

        // 3. Now that we know the rows are there, get the final list
        List<WebElement> cartItems = driver.findElements(By.cssSelector("#tbodyid tr.success"));

        // 4. TestNG Assertion
        Assert.assertTrue(
                cartItems.size() >= expectedCount,
                "Cart validation failed! Expected at least " + expectedCount + " items, but found: " + cartItems.size()
        );
    }

    @Given("I have a product in the cart")
    public void iHaveAProductInTheCart() {
        // 1. Go to homepage and add the first product
        driver.get("https://www.demoblaze.com/");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.hrefch"))).click();

        // 2. Click 'Add to cart'
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Add to cart')]"))).click();

        // 3. Handle Alert
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    @When("I remove the product from the cart")
    public void iRemoveTheProductFromTheCart() {
        // 1. Navigate to Cart page (using the text link we identified)
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Cart')]"))).click();

        // 2. Wait for items to appear (class='success' as seen in your HTML)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#tbodyid tr.success")));

        // 3. Click the first 'Delete' link available in the table
        // We use the text 'Delete' inside the table body
        driver.findElement(By.xpath("//tbody[@id='tbodyid']//a[contains(text(),'Delete')]")).click();

        // 4. Wait for the row to disappear (this is crucial for synchronization)
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#tbodyid tr.success")));
    }

    @Then("the cart should be empty")
    public void theCartShouldBeEmpty() {
        // 1. Find all rows in the cart body
        List<WebElement> cartRows = driver.findElements(By.cssSelector("#tbodyid tr"));

        // 2. Assert that the size is 0
        Assert.assertEquals(cartRows.size(), 0, "The cart was expected to be empty, but it still contains items!");
    }
// Negative Test 
    @When("I proceed to checkout")
    public void iProceedToCheckout() {
        // 1. FIX: Navigate to the Cart Page first
        // The previous error happened because we were still on the Product Page
        wait.until(ExpectedConditions.elementToBeClickable(By.id("cartur"))).click();

        // 2. Click 'Place Order' to open the modal
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Place Order']"))).click();

        // 3. Wait for the Modal form to be visible so the next step doesn't fail
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("orderModal")));
    }

    @And("I submit the order without filling the form")
    public void iSubmitTheOrderWithoutFillingTheForm() {
        // Since the modal is already open from the previous step, we just click 'Purchase'
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Purchase']"))).click();
    }

    @Then("an error message should be displayed")
    public void anErrorMessageShouldBeDisplayed() {
        // 1. Wait for the browser popup (Alert) to appear
        wait.until(ExpectedConditions.alertIsPresent());

        // 2. Switch focus to the alert and get the text
        Alert alert = driver.switchTo().alert();
        String actualErrorText = alert.getText();

        // 3. Verify the text matches Demoblaze's validation message
        Assert.assertTrue(
                actualErrorText.contains("Please fill out Name and Creditcard."),
                "Expected validation error not found! Got: " + actualErrorText
        );

        // 4. IMPORTANT: Close the alert so the browser doesn't freeze
        alert.accept();
    }

    @And("I fill the order form with valid data")
    public void iFillTheOrderFormWithValidData() {
        // 1. Ensure the modal is visible before typing
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("orderModal")));

        // 2. Fill in the form fields using their IDs
        driver.findElement(By.id("name")).sendKeys("Test User");
        driver.findElement(By.id("country")).sendKeys("USA");
        driver.findElement(By.id("city")).sendKeys("New York");
        driver.findElement(By.id("card")).sendKeys("1234567890123456");
        driver.findElement(By.id("month")).sendKeys("12");
        driver.findElement(By.id("year")).sendKeys("2025");
    }

    @And("I confirm the purchase")
    public void iConfirmThePurchase() {
        // Click the 'Purchase' button at the bottom of the modal
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Purchase']"))).click();
    }

    @Then("the purchase should be successful")
    public void thePurchaseShouldBeSuccessful() {
        // 1. Wait for the success 'SweetAlert' dialog to appear
        // The success message header is in an <h2> tag with specific text
        WebElement successHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[contains(text(),'Thank you for your purchase!')]")));

        // 2. Assert the header is displayed
        Assert.assertTrue(successHeader.isDisplayed(), "Success message was not displayed!");

        // 3. (Optional) Print the confirmation details (ID, Amount, etc.) for debugging
        String orderDetails = driver.findElement(By.cssSelector("p.lead")).getText();
        System.out.println("Order Confirmation Details: \n" + orderDetails);

        // 4. Click 'OK' to close the modal and reset the state
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='OK']"))).click();
    }
}

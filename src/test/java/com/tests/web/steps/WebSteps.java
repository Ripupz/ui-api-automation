package com.tests.web.steps;


import com.tests.web.utils.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class WebSteps {

    WebDriver driver = DriverFactory.getDriver();


    @Given("I open the homepage")
    public void iOpenTheHomepage() {
        driver.get("https://www.demoblaze.com/");
    }

    @Then("the page title should contain {string}")
    public void thePageTitleShouldContain(String expected) {
        String actual = driver.getTitle();
        assertTrue(
                "Expected title to contain: " + expected + " but got: " + actual,
                actual.contains(expected)
        );
    }

    @When("I add the first product to cart")
    public void iAddTheFirstProductToCart() {
        driver.findElement(By.cssSelector("#tbodyid .col-lg-4.col-md-6.mb-4 a")).click();
        driver.findElement(By.cssSelector(".btn.btn-success.btn-lg")).click();
    }

    @Then("the cart should contain at least {int} item")
    public void theCartShouldContainAtLeastItem(Integer count) {
        driver.get("https://www.demoblaze.com/cart.html");

        int items = driver.findElements(By.cssSelector("#tbodyid tr")).size();
        assertTrue("Cart contains only " + items + " items", items >= count);
    }
}

package com.tests.web.steps;


import com.tests.web.utils.DriverFactory;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class WebSteps {

    WebDriver driver = DriverFactory.getDriver();

    // Verify Homepage
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

    // View Product Details
    @When("I click the first product")
    public void iClickTheFirstProduct() {

    }

    @Then("the product detail page should be displayed")
    public void theProductDetailPageShouldBeDisplayed() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}

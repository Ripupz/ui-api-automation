package com.tests.api.steps;


import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

public class ApiSteps {

    private static final String BASE = "https://dummyapi.io/data/v1";
    private static final String APP_ID = "63a804408eb0cb069b57e43a";
    private Map<String, String> userData;


    private Response lastResponse;
    private String createdUserId;

    // Background
    @Given("the API has app-id set")
    public void theAPIHasAppIdSet() {
        RestAssured.baseURI = BASE;
    }

    // List of tags

    @When("I request GET \\/tag")
    public void iRequestGETTag() {
        lastResponse = RestAssured.given()
                .header("app-id", APP_ID)
                .when()
                .get("/tag")
                .then()
                .extract()
                .response();
    }


    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int expectedStatus) {
        Assert.assertEquals(expectedStatus, lastResponse.getStatusCode());
    }

    @And("the response should contain a list of tags")
    public void theResponseShouldContainAListOfTags() {
        Assert.assertTrue(lastResponse.jsonPath().getList("data").size() > 0);
    }

    // ---------------- CREATE USER -----------------

    @Given("I have user data with firstName {string}, lastName {string}, and email {string}")
    public void iHaveUserDataWithFirstNameLastNameAndEmail(String firstName, String lastName, String email) {
        userData = new HashMap<>();
        userData.put("firstName", firstName);
        userData.put("lastName", lastName);
        String uniqueEmail = "john" + System.currentTimeMillis() + "@example.com";
        userData.put("email", uniqueEmail);

    }

    @When("I create the user")
    public void iCreateTheUser() {
        lastResponse = RestAssured.given()
                .header("app-id", APP_ID)
                .contentType("application/json")
                .body(userData)
                .when()
                .post("/user/create")
                .then()
                .extract()
                .response();

        createdUserId = lastResponse.jsonPath().getString("id");
        System.out.println("Create Response: " + lastResponse.getBody().asString());
    }

    @Then("the create response status should be {int}")
    public void theCreateResponseStatusShouldBe(int expectedStatus) {
        Assert.assertEquals(expectedStatus, lastResponse.getStatusCode());
    }

    @And("the response contains firstName {string}")
    public void theResponseContainsFirstName(String expectedFirstName) {
        // pastikan ambil firstName langsung dari response
        String actualFirstName = lastResponse.jsonPath().getString("firstName");
        Assert.assertEquals(expectedFirstName, actualFirstName);
    }

    // ---------------- UPDATE USER -----------------

    @And("I update the user's firstName to {string}")
    public void iUpdateTheUserSFirstNameTo(String newName) {
        Map<String, String> body = new HashMap<>();
        body.put("firstName", newName);

        lastResponse = RestAssured.given()
                .header("app-id", APP_ID)
                .contentType("application/json")
                .body(body)
                .when()
                .put("/user/" + createdUserId)
                .then()
                .extract()
                .response();
        System.out.println(lastResponse.getBody().asString());
    }

    @Then("the update response status should be {int}")
    public void theUpdateResponseStatusShouldBe(int expectedStatus) {
        Assert.assertEquals(expectedStatus, lastResponse.getStatusCode());
        System.out.println(lastResponse.getBody().asString());
    }

    // ---------------- DELETE USER -----------------

    @And("I delete the user")
    public void iDeleteTheUser() {
        lastResponse = RestAssured.given()
                .header("app-id", APP_ID)
                .when()
                .delete("/user/" + createdUserId)
                .then()
                .extract()
                .response();
    }

    @Then("the delete response status should be {int}")
    public void theDeleteResponseStatusShouldBe(int expectedStatus) {
        Assert.assertEquals(expectedStatus, lastResponse.getStatusCode());
    }


    // Get List of user

    @When("I request GET \\/user")
    public void iRequestGETUser() {
        lastResponse = RestAssured.given()
                .header("app-id", APP_ID)
                .when()
                .get("/user")
                .then()
                .extract()
                .response();
    }


    @And("the response should contain a list of users")
    public void theResponseShouldContainAListOfUsers() {
        Assert.assertTrue(lastResponse.jsonPath().getList("data").size() > 0);
    }


    // Get User by ID

    @Given("I have an existing user with firstName {string}, lastName {string}, email {string}")
    public void iHaveAnExistingUserWithFirstNameLastNameEmail(String firstName, String lastName, String email) {
        // Inisialisasi userData
        userData = new HashMap<>();

        // Isi data user
        userData.put("firstName", firstName);
        userData.put("lastName", lastName);
        String uniqueEmail = "Rini" + System.currentTimeMillis() + "@example.com";
        userData.put("email", uniqueEmail);

        // POST untuk membuat user
        lastResponse = RestAssured.given()
                .header("app-id", APP_ID)
                .contentType("application/json")
                .body(userData)
                .when()
                .post("/user/create")
                .then()
                .extract()
                .response();

        // Simpan ID untuk GET /user/{id}
        createdUserId = lastResponse.jsonPath().getString("id");
    }


    @When("I request GET \\/user\\/\\{id}")
    public void iRequestGETUserId() {
        // Gunakan createdUserId dari step sebelumnya
        lastResponse = RestAssured.given()
                .header("app-id", APP_ID)
                .when()
                .get("/user/" + createdUserId)
                .then()
                .extract()
                .response();
    }

    // Negative Api Test

    @Given("I have user data with firstName {string} and lastName {string} but no email")
    public void iHaveUserDataWithFirstNameAndLastNameButNoEmail(String firstName, String lastName) {
        // Build JSON without email
        Map<String, String> body = new HashMap<>();
        body.put("firstName", firstName);
        body.put("lastName", lastName);

        // Simpan ke variable global untuk dipakai di step create user
        userData = body;
    }


    // Get user with Invalid ID
    @When("I request GET \\/user\\/invalid-id")
    public void iRequestGETUserInvalidId() {
        // Kirim request GET dengan ID yang invalid
        lastResponse = RestAssured.given()
                .header("app-id", APP_ID)
                .when()
                .get("/user/invalid-id")
                .then()
                .extract()
                .response();
    }
}

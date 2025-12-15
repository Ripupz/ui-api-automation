package com.tests.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.tests.web.steps","com.tests.api.steps"},
        plugin = {
        "pretty",
        "html:target/cucumber-html-report",
        "json:target/cucumber.json"
        },
        snippets = CucumberOptions.SnippetType.UNDERSCORE
)
public class TestRunner { }

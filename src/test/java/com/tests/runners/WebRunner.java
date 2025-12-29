package com.tests.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@CucumberOptions(
        features = "src/test/resources/features/web", // Path to Web features
        glue = {"com.tests.web.steps"},               // Path to Web steps
        plugin = {
                "pretty",
                "html:build/reports/cucumber/web-report.html",
                "json:build/reports/cucumber/web-report.json"
        }
)
public class WebRunner extends AbstractTestNGCucumberTests {
    @Override
    @Test(groups = "cucumber", dataProvider = "scenarios")
    public void runScenario(io.cucumber.testng.PickleWrapper pickleWrapper, io.cucumber.testng.FeatureWrapper featureWrapper) {
        super.runScenario(pickleWrapper, featureWrapper);
    }

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
package com.tests.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@CucumberOptions(
        features = "src/test/resources/features/api", // Path to API features
        glue = {"com.tests.api.steps"},               // Path to API steps
        plugin = {
                "pretty",
                "html:build/reports/cucumber/api-report.html",
                "json:build/reports/cucumber/api-report.json"
        }
)
public class ApiRunner extends AbstractTestNGCucumberTests {
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
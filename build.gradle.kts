plugins {
    java
}

group = "com.tests"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    // Cucumber
    testImplementation("io.cucumber:cucumber-java:7.31.0")
    testImplementation("io.cucumber:cucumber-testng:7.31.0")

    // TestNG
    testImplementation("org.testng:testng:7.10.2")

    // Selenium
    testImplementation ("org.seleniumhq.selenium:selenium-java:4.38.0")

    // RestAssured for API tests
    testImplementation("io.rest-assured:rest-assured:5.3.0")
    testImplementation("io.rest-assured:json-path:5.3.0")

    // WebDriver Manager
    testImplementation("org.seleniumhq.selenium:selenium-firefox-driver:4.11.0")
    testImplementation("io.github.bonigarcia:webdrivermanager:5.9.2")


    // JSON (optional)
    testImplementation("com.google.code.gson:gson:2.10.1")
}

tasks.test {
    useTestNG {
        suites("testng.xml")
    }


    testLogging {
        events("passed", "skipped", "failed", "standardOut", "standardError")
        showStandardStreams = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

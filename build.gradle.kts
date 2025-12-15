plugins {
    java
}

group = "com.example"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

dependencies {
    // Cucumber
    testImplementation("io.cucumber:cucumber-java:7.31.0")
    testImplementation("io.cucumber:cucumber-junit:7.31.0")

    // Selenium
    testImplementation ("org.seleniumhq.selenium:selenium-java:4.38.0")

    // RestAssured for API tests
    testImplementation("io.rest-assured:rest-assured:5.3.0")
    testImplementation("io.rest-assured:json-path:5.3.0")

    // WebDriver Manager
    testImplementation("org.seleniumhq.selenium:selenium-firefox-driver:4.11.0")
    testImplementation("io.github.bonigarcia:webdrivermanager:5.9.2")

    // JUnit
    testImplementation("junit:junit:4.13.2")

    // JSON (optional)
    testImplementation("com.google.code.gson:gson:2.10.1")
}

// Custom JavaExec tasks to run cucumber CLI by tag
tasks.register<JavaExec>("runApiTests") {
    group = "verification"
    description = "Run Cucumber API tests (@api)"
    mainClass.set("io.cucumber.core.cli.Main")
    classpath = sourceSets["test"].runtimeClasspath
    args = listOf(
        "--plugin", "pretty",
        "--plugin", "html:build/reports/cucumber/api.html",
        "--plugin", "json:build/reports/cucumber/api.json",
        "--glue", "com.example.tests.api.steps",
        "src/test/resources/features/api",
        "--tags", "@api"
    )
}

tasks.register<JavaExec>("runWebTests") {
    group = "verification"
    description = "Run Cucumber Web tests (@web)"
    mainClass.set("io.cucumber.core.cli.Main")
    classpath = sourceSets["test"].runtimeClasspath
    args = listOf(
        "--plugin", "pretty",
        "--plugin", "html:build/reports/cucumber/web.html",
        "--plugin", "json:build/reports/cucumber/web.json",
        "--glue", "com.example.tests.web.steps",
        "src/test/resources/features/web",
        "--tags", "@web"
    )
}

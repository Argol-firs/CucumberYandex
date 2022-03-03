package tests;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/tests/features",
        plugin = {"pretty", "json:target/cucumber-report/report.json", "io.qameta.allure.cucumber4jvm.AllureCucumber4Jvm"},
        glue = "tests.stepDef"
)

public class RunnerTest {

}
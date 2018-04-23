package stepdefs;

import cucumber.api.CucumberOptions;
import cucumber.api.java.Before;
import org.junit.runner.RunWith;

import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
//@CucumberOptions(features = {"src/test/resources/features"}, format = {"pretty", "html:target/reports/cucumber/html",
//        "json:target/cucumber.json", "usage:target/usage.jsonx", "junit:target/junit.xml"})
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefs"},
        tags = {"~@Ignore"},
        format = {
                "pretty",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "rerun:target/cucumber-reports/rerun.txt"
        })
public class CucumberAcceptanceTest {


}

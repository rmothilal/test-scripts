package stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.ResponseEntity;


import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import static utils.UtilityClass.getNewCorrelationId;

public class UserOnboardingIntegrationTest extends SpringIntegrationTest {

    RequestSpecification reqSpec;
    Response raResponse;

    String correlationId;

    String mojaloopHost = "13.58.148.157";
    String mojaloopUrl = "http://"+mojaloopHost+":8088/interop/switch/v1";

    private Logger logger = Logger.getLogger(UserOnboardingIntegrationTest.class.getName());

    @Given("^user \"([^\"]*)\" does not exist in central directory$")
    public void userDoesNotExistInCentralDirectory(String phNum) throws Throwable {
        assertTrue(true);
    }

    @When("^user \"([^\"]*)\" that is in \"([^\"]*)\" is added in central directory$")
    public void userThatIsInIsAddedInCentralDirectory(String phNum, String dfspName) throws Throwable {
        correlationId = getNewCorrelationId();
        logger.info("Url: "+mojaloopUrl+"/participants/MSISDN/" + phNum);
        raResponse = given()
                        .body("{\"fspId\": \"test-dfsp1\",\"currency\": \"USD\"}")
                        .header("FSPIOP-Source","test-dfsp1")
                        .header("X-Forwarded-For",correlationId)
                        .header("Content-Type", "application/json")
                    .when()
                        .post(mojaloopUrl + "/participants/MSISDN/" + phNum);
        assertThat(raResponse.getStatusCode(),is(200));

    }

    @Then("^response should contain \"([^\"]*)\" name$")
    public void responseShouldContainName(String dfspName) throws Throwable {
        Thread.sleep(2000);
        ResponseEntity<String> response = restTemplate.getForEntity("/correlationid/"+correlationId,String.class);

        assertThat(response.getBody(),containsString(dfspName));
        //assertTrue(true);
    }

    @Given("^user \"([^\"]*)\" exists in central directory$")
    public void userExistsInCentralDirectory(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^user \"([^\"]*)\" is deleted from central directory$")
    public void userIsDeletedFromCentralDirectory(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^upon further lookup, the result should be empty$")
    public void uponFurtherLookupTheResultShouldBeEmpty() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}


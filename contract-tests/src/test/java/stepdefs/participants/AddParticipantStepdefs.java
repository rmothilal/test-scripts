package stepdefs.participants;

import com.mojaloop.utils.Utility;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.ResponseEntity;
import stepdefs.SpringAcceptanceTest;

import javax.json.Json;
import java.util.logging.Logger;

import static com.mojaloop.utils.Utility.getRestTemplate;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddParticipantStepdefs extends SpringAcceptanceTest {

    private Logger logger = Logger.getLogger(AddParticipantStepdefs.class.getName());

    ResponseEntity<String> response;
    String responseJson;

    String mojaloopHost = System.getProperty("mojaloop.host");
    String mojaloopUrl = "http://"+mojaloopHost+":8088/interop/switch/v1";


    @When("^I send a request to POST /participants with  \"([^\"]*)\" \"([^\"]*)\" and  \"([^\"]*)\" with  \"([^\"]*)\"$")
    public void iSendARequestToPOSTParticipantsWithAndWith(String type, String msisdn, String fspId, String currency) throws Throwable {
        String requestJson = Json.createObjectBuilder()
                .add("fspId", fspId)
                .add("currency",currency)
                .build().toString();
        responseJson = Utility.post(mojaloopUrl + "/participants/MSISDN/" + msisdn,fspId,null,null,requestJson,getRestTemplate());
    }

    @Then("^the participant information should be added in the switch\\. Expected FspID in the response is \"([^\"]*)\"$")
    public void theParticipantInformationShouldBeAddedInTheSwitchExpectedFspIDInTheResponseIs(String expectedFspID) throws Throwable {
        assertThat(responseJson,containsString(expectedFspID));
    }

    @When("^I send a request to POST /participants with Type is \"([^\"]*)\", ID is \"([^\"]*)\" and FspID \"([^\"]*)\" and do not pass \"([^\"]*)\" in the request$")
    public void iSendARequestToPOSTParticipantsWithTypeIsIDIsAndFspIDAndDoNotPassInTheRequest(String type, String msisdn, String fspId, String currency) throws Throwable {
        String requestJson = Json.createObjectBuilder()
                .add("fspId", fspId)
                .build().toString();
        responseJson = Utility.post(mojaloopUrl + "/participants/MSISDN/" + msisdn,fspId,null,null,requestJson,getRestTemplate());
    }

    @Then("^the participant information should be added in the switch without currency\\. Expected FspID in the response is \"([^\"]*)\"$")
    public void theParticipantInformationShouldBeAddedInTheSwitchWithoutCurrencyExpectedFspIDInTheResponseIs(String expectedFspID) throws Throwable {
        assertThat(responseJson,containsString(expectedFspID));
    }

    @Given("^a participant with MSISDN \"([^\"]*)\" exists in switch with a \"([^\"]*)\"$")
    public void aParticipantExistsInSwitchWithA(String msisdn, String fspId) throws Throwable {
        responseJson = Utility.get(mojaloopUrl + "/participants/MSISDN/" + msisdn,fspId,null,null,getRestTemplate());
        assertThat(responseJson,containsString(fspId));
    }

    @When("^I add the participant with the same \"([^\"]*)\" , \"([^\"]*)\" and \"([^\"]*)\" to the switch$")
    public void iAddTheParticipantWithTheSameAndToTheSwitch(String type, String id, String fspId) throws Throwable {
        String requestJson = Json.createObjectBuilder()
                .add("fspId", fspId)
                .build().toString();
        responseJson = Utility.post(mojaloopUrl + "/participants/MSISDN/" + id,fspId,null,null,requestJson,getRestTemplate());
    }

    @Then("^I should get a response \"([^\"]*)\"$")
    public void iShouldGetAResponse(String expectedErrorMessage) throws Throwable {
        assertThat(responseJson,containsString(expectedErrorMessage));
    }

    @When("^I send a request to POST /participants with \"([^\"]*)\" and one of these fileds missing \"([^\"]*)\" \"([^\"]*)\" in the request$")
    public void iSendARequestToPOSTParticipantsWithAndOneOfTheseFiledsMissingInTheRequest(String type, String id, String fspId) throws Throwable {
        String requestJson = Json.createObjectBuilder()
                .add("fspId", fspId)
                .build().toString();
        responseJson = Utility.post(mojaloopUrl + "/participants/MSISDN/" + id,fspId,null,null,requestJson,getRestTemplate());
    }

    @Then("^An error should be returned\\. Expected error code is \"([^\"]*)\"$")
    public void anErrorShouldBeReturnedExpectedErrorCodeIs(String expectedErrorCode) throws Throwable {
        assertThat(responseJson,containsString(expectedErrorCode));
    }

    @And("^error description is \"([^\"]*)\"$")
    public void errorDescriptionIs(String expectedErrorDescription) throws Throwable {
        assertThat(responseJson,containsString(expectedErrorDescription));
    }

    @When("^I send a request to POST /participants with an invalid FspID  \"([^\"]*)\", a valid Type \"([^\"]*)\" and \"([^\"]*)\" in the request$")
    public void iSendARequestToPOSTParticipantsWithAnInvalidFspIDAValidTypeAndInTheRequest(String arg0, String arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I send a request to POST /participants with a valid FspID  \"([^\"]*)\" and invalid Type \"([^\"]*)\" in the request$")
    public void iSendARequestToPOSTParticipantsWithAValidFspIDAndInvalidTypeInTheRequest(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I send a request to POST /participants with a valid FspID \"([^\"]*)\" and valid \"([^\"]*)\" invalid ID \"([^\"]*)\" in the request$")
    public void iSendARequestToPOSTParticipantsWithAValidFspIDAndValidInvalidIDInTheRequest(String arg0, String arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^Currency$")
    public void currency() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I send POST /participant request with \"([^\"]*)\"<FspID>\"([^\"]*)\"<Type>\"([^\"]*)\"<ID>\"$")
    public void iSendPOSTParticipantRequestWithFspIDTypeID(String arg0, String arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I should see the \"([^\"]*)\"$")
    public void iShouldSeeThe(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}


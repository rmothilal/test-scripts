package stepdefs.participants;

import com.mojaloop.utils.Utility;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.ResponseEntity;
import stepdefs.SpringAcceptanceTest;

import javax.json.Json;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddParticipantStepDefs extends SpringAcceptanceTest {

    ResponseEntity<String> response;

    String responseJson;

    String mojaloopHost = "13.58.148.157";
    String mojaloopUrl = "http://"+mojaloopHost+":8088/interop/switch/v1";

    @When("^I send a request to POST /participants with  \"([^\"]*)\" and  \"([^\"]*)\" with  \"([^\"]*)\"$")
    public void iSendARequestToPOSTParticipantsWithAndWith(String msisdn, String fspId, String currency) throws Throwable {
        String requestJson = Json.createObjectBuilder()
                .add("fspId", fspId)
                .add("currency","USD")
                .build().toString();
        responseJson = Utility.post(mojaloopUrl + "/participants/MSISDN/" + msisdn,fspId,null,null,requestJson,restTemplate);
    }

    @Then("^the participant information should be added in the switch\\. Expected FspID in the response is \"([^\"]*)\"$")
    public void theParticipantInformationShouldBeAddedInTheSwitchExpectedFspIDInTheResponseIs(String expectedFspId) throws Throwable {
        assertThat(responseJson,containsString(expectedFspId));
    }

    @When("^I send a request to POST /participants with \"([^\"]*)\" and FspID \"([^\"]*)\" and do not pass currency in the request$")
    public void iSendARequestToPOSTParticipantsWithAndFspIDAndDoNotPassCurrencyInTheRequest(String msisdn, String fspId) throws Throwable {
        String requestJson = Json.createObjectBuilder()
                .add("fspId", fspId)
                .build().toString();
        responseJson = Utility.post(mojaloopUrl + "/participants/MSISDN/" + msisdn,fspId,null,null,requestJson,restTemplate);
    }

    @When("^I send a request to POST /participants with \"([^\"]*)\" and do not pass FspID in the request$")
    public void iSendARequestToPOSTParticipantsWithAndDoNotPassFspIDInTheRequest(String msisdn) throws Throwable {
        String requestJson = Json.createObjectBuilder()
                .build().toString();
        responseJson = Utility.post(mojaloopUrl + "/participants/MSISDN/" + msisdn,"",null,null,requestJson,restTemplate);
    }

    @Then("^An error should be returned\\. Expected error code is \"([^\"]*)\" and error description is \"([^\"]*)\"$")
    public void anErrorShouldBeReturnedExpectedErrorCodeIsAndErrorDescriptionIs(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I send a request to POST /participants for \"([^\"]*)\"  in \"([^\"]*)\"in the request and do not pass participants type$")
    public void iSendARequestToPOSTParticipantsForInInTheRequestAndDoNotPassParticipantsType(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I send a request to POST /participants with FspID  \"([^\"]*)\" and Type \"([^\"]*)\" in the request and do not pass participants ID$")
    public void iSendARequestToPOSTParticipantsWithFspIDAndTypeInTheRequestAndDoNotPassParticipantsID(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I send a request to POST /participants with FspID  \"([^\"]*)\" and invalid ID \"([^\"]*)\" in the request$")
    public void iSendARequestToPOSTParticipantsWithFspIDAndInvalidIDInTheRequest(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}

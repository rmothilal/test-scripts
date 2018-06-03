package stepdefs.participants;

import com.mojaloop.utils.MLResponse;
import com.mojaloop.utils.Utility;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.path.json.JsonPath;
import stepdefs.SpringAcceptanceTest;

import javax.json.Json;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static com.mojaloop.utils.Utility.getRestTemplate;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetParticipantStepdefs extends SpringAcceptanceTest {

    private Logger logger = Logger.getLogger(GetParticipantStepdefs.class.getName());

    MLResponse response;

    String participantsBaseUrl = mojaloopHost+"/interop/switch/v1/participants";

    @Given("^Payee \"([^\"]*)\" with \"([^\"]*)\" exists in switch under \"([^\"]*)\"$")
    public void payeeWithExistsInSwitchUnder(String payeeid, String type, String payeefspid) throws Throwable {
        String requestJson = Json.createObjectBuilder()
                .add("fspId", payeefspid)
                .build().toString();

        Map<String,String> headers = new HashMap<>();
        headers.put("Accept","application/vnd.interoperability.participants+json;version=1");
        headers.put("FSPIOP-Source","payerfsp");

        response = Utility.post(String.join(participantsBaseUrl).join("/",type).join("/", payeeid),requestJson,headers, null,getRestTemplate());
    }

    @When("^Payer FSP does a lookup for payee \"([^\"]*)\" and type \"([^\"]*)\" in the switch$")
    public void payerFSPDoesALookupForPayeeAndTypeInTheSwitch(String payeeid, String type) throws Throwable {
        Map<String,String> headers = new HashMap<>();
        headers.put("Accept","application/vnd.interoperability.participants+json;version=1");
        headers.put("FSPIOP-Source","payerfsp");

        response = Utility.get(String.join(participantsBaseUrl).join("/",type).join("/", payeeid),headers, null,getRestTemplate());
    }

    @Then("^Payee FSP information \"([^\"]*)\" should be returned\\.$")
    public void payeeFSPInformationShouldBeReturned(String expectedPayeeFspID) throws Throwable {
        assertThat(response.getResponseCode(), is("202"));
        assertThat(response.getResponseBody(),containsString(expectedPayeeFspID));
    }

    @Given("^Payee \"([^\"]*)\" with \"([^\"]*)\" does not exist in switch$")
    public void payeeWithDoesNotExistInSwitch(String payeeid, String type) throws Throwable {
        Map<String,String> headers = new HashMap<>();
        headers.put("Accept","application/vnd.interoperability.participants+json;version=1");
        headers.put("FSPIOP-Source","payerfsp");

        response = Utility.get(String.join(participantsBaseUrl).join("/",type).join("/", payeeid),headers, null,getRestTemplate());

        JsonPath jPath = JsonPath.from(response.getResponseBody());
        assertThat(jPath.getString("errorInformation.errorCode"), is("3201"));
        assertThat(jPath.getString("errorInformation.errorDescription"), is("3201"));
    }

    @When("^Payer FSP does a lookup for non existing payee \"([^\"]*)\" and type \"([^\"]*)\" in the switch$")
    public void payerFSPDoesALookupForNonExistingPayeeAndTypeInTheSwitch(String payeeid, String type) throws Throwable {
        Map<String,String> headers = new HashMap<>();
        headers.put("Accept","application/vnd.interoperability.participants+json;version=1");
        headers.put("FSPIOP-Source","payerfsp");

        response = Utility.get(String.join(participantsBaseUrl).join("/",type).join("/", payeeid),headers, null,getRestTemplate());
    }

    @Then("^error should be returned\\. Expected values are \"([^\"]*)\" and \"([^\"]*)\"$")
    public void errorShouldBeReturnedExpectedValuesAreAnd(String expectedErrorCode, String expectedErrorDescription) throws Throwable {
        JsonPath jPath = JsonPath.from(response.getResponseBody());
        assertThat(jPath.getString("errorInformation.errorCode"), is(expectedErrorCode));
        assertThat(jPath.getString("errorInformation.errorDescription"), is(expectedErrorDescription));
    }

    @When("^I send GET /participants request with missing header \"([^\"]*)\"$")
    public void iSendGETParticipantsRequestWithMissingHeader(String header) throws Throwable {
        Map<String,String> headers = new HashMap<>();
        headers.put("Accept","application/vnd.interoperability.participants+json;version=1");

        response = Utility.get(String.join(participantsBaseUrl).join("/","MSISDN").join("/", "1272545118"),headers, null,getRestTemplate());
    }

    @Then("^I should get an error with error code \"([^\"]*)\" and the error message containing missing header \"([^\"]*)\"$")
    public void iShouldGetAnErrorWithErrorCodeAndTheErrorMessageContainingMissingHeader(String expectedErrorCode, String expectedErrorDescription) throws Throwable {
        JsonPath jPath = JsonPath.from(response.getResponseBody());
        assertThat(jPath.getString("errorInformation.errorCode"), is(expectedErrorCode));
        assertThat(jPath.getString("errorInformation.errorDescription"), containsString(expectedErrorDescription));
    }

    @When("^I send a request to GET /participants with a valid \"([^\"]*)\" and an invalid Type \"([^\"]*)\" in the request$")
    public void iSendARequestToGETParticipantsWithAValidAndAnInvalidTypeInTheRequest(String payeeid, String type) throws Throwable {
        Map<String,String> headers = new HashMap<>();
        headers.put("Accept","application/vnd.interoperability.participants+json;version=1");
        headers.put("FSPIOP-Source","payerfsp");

        response = Utility.get(String.join(participantsBaseUrl).join("/",type).join("/", payeeid),headers, null,getRestTemplate());
    }

    @Then("^Return Http response code for invald type is \"([^\"]*)\"$")
    public void returnHttpResponseCodeForInvaldTypeIs(String expectedHttpResponseCode) throws Throwable {
        assertThat(response.getResponseCode(), is(expectedHttpResponseCode));
    }

    @And("^Error code for invalid type is \"([^\"]*)\"$")
    public void errorCodeForInvalidTypeIs(String expectedErrorCode) throws Throwable {
        JsonPath jPath = JsonPath.from(response.getResponseBody());
        assertThat(jPath.getString("errorInformation.errorCode"), is(expectedErrorCode));
    }


    @When("^I send a request to GET /participants with a valid \"([^\"]*)\" invalid ID \"([^\"]*)\" in the request$")
    public void iSendARequestToGETParticipantsWithAValidInvalidIDInTheRequest(String type, String payeeid) throws Throwable {
        Map<String,String> headers = new HashMap<>();
        headers.put("Accept","application/vnd.interoperability.participants+json;version=1");
        headers.put("FSPIOP-Source","payerfsp");

        response = Utility.get(String.join(participantsBaseUrl).join("/",type).join("/", payeeid),headers, null,getRestTemplate());
    }

    @Then("^An error should be returned\\. Expected error code for invalid ID is \"([^\"]*)\"$")
    public void anErrorShouldBeReturnedExpectedErrorCodeForInvalidIDIs(String expectedErrorCode) throws Throwable {
        JsonPath jPath = JsonPath.from(response.getResponseBody());
        assertThat(jPath.getString("errorInformation.errorCode"), is(expectedErrorCode));
    }

    @And("^error description for invalid ID is \"([^\"]*)\"$")
    public void errorDescriptionForInvalidIDIs(String expectedErrorDescription) throws Throwable {
        JsonPath jPath = JsonPath.from(response.getResponseBody());
        assertThat(jPath.getString("errorInformation.errorDescription"), is(expectedErrorDescription));
    }

    @And("^Http response code for invalid ID is \"([^\"]*)\"$")
    public void httpResponseCodeForInvalidIDIs(String expectedResponseCode) throws Throwable {
        assertThat(response.getResponseCode(),is(expectedResponseCode));
    }
}

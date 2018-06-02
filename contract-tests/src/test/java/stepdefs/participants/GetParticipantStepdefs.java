package stepdefs.participants;

import com.mojaloop.utils.MLResponse;
import com.mojaloop.utils.Utility;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import stepdefs.SpringAcceptanceTest;

import javax.json.Json;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static com.mojaloop.utils.Utility.getRestTemplate;

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
        headers.put("Accept","");
        headers.put("FSPIOP-Source","payerfsp");

        response = Utility.post(String.join(participantsBaseUrl).join("/",type).join("/", payeeid),requestJson,headers, null,getRestTemplate());
    }

    @When("^Payer FSP does a lookup for payee \"([^\"]*)\" and type \"([^\"]*)\" in the switch$")
    public void payerFSPDoesALookupForPayeeAndTypeInTheSwitch(String payeeid, String type) throws Throwable {
        Map<String,String> headers = new HashMap<>();
        headers.put("Accept","");
        headers.put("FSPIOP-Source","payerfsp");

        response = Utility.get(String.join(participantsBaseUrl).join("/",type).join("/", payeeid),headers, null,getRestTemplate());
    }

    @Then("^Payee FSP information \"([^\"]*)\" should be returned\\.$")
    public void payeeFSPInformationShouldBeReturned(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}

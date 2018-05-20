package stepdefs.participants;

import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AddParticipantStepDefs {

    @When("^I send a request to POST /participants with  \"([^\"]*)\" and  \"([^\"]*)\" with  \"([^\"]*)\"$")
    public void iSendARequestToPOSTParticipantsWithAndWith(String arg0, String arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the participant information should be added in the switch\\. Expected FspID in the response is \"([^\"]*)\"$")
    public void theParticipantInformationShouldBeAddedInTheSwitchExpectedFspIDInTheResponseIs(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I send a request to POST /participants with \"([^\"]*)\" and FspID \"([^\"]*)\" and do not pass currency in the request$")
    public void iSendARequestToPOSTParticipantsWithAndFspIDAndDoNotPassCurrencyInTheRequest(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I send a request to POST /participants with \"([^\"]*)\" and do not pass FspID in the request$")
    public void iSendARequestToPOSTParticipantsWithAndDoNotPassFspIDInTheRequest(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
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

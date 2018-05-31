package stepdefs.participants;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AddParticipantStepdefs {
    @Then("^the participant information should be added in the switch\\. Expected FspID in the response is \"([^\"]*)\"$")
    public void theParticipantInformationShouldBeAddedInTheSwitchExpectedFspIDInTheResponseIs(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I send a request to POST /participants with  \"([^\"]*)\" \"([^\"]*)\" and  \"([^\"]*)\" with  \"([^\"]*)\"$")
    public void iSendARequestToPOSTParticipantsWithAndWith(String arg0, String arg1, String arg2, String arg3) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I send a request to POST /participants with Type is \"([^\"]*)\", ID is \"([^\"]*)\" and FspID \"([^\"]*)\" and do not pass \"([^\"]*)\" in the request$")
    public void iSendARequestToPOSTParticipantsWithTypeIsIDIsAndFspIDAndDoNotPassInTheRequest(String arg0, String arg1, String arg2, String arg3) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^a participant exists in switch with a \"([^\"]*)\"$")
    public void aParticipantExistsInSwitchWithA(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I add the participant with the same \"([^\"]*)\" , \"([^\"]*)\" and \"([^\"]*)\" to the switch$")
    public void iAddTheParticipantWithTheSameAndToTheSwitch(String arg0, String arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I should get a response \"([^\"]*)\"$")
    public void iShouldGetAResponse(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I send a request to POST /participants with \"([^\"]*)\" and one of these fileds missing \"([^\"]*)\" \"([^\"]*)\" in the request$")
    public void iSendARequestToPOSTParticipantsWithAndOneOfTheseFiledsMissingInTheRequest(String arg0, String arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^An error should be returned\\. Expected error code is \"([^\"]*)\"$")
    public void anErrorShouldBeReturnedExpectedErrorCodeIs(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^error description is \"([^\"]*)\"$")
    public void errorDescriptionIs(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^Http Response code is \"([^\"]*)\"$")
    public void httpResponseCodeIs(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
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

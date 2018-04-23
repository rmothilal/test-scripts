package stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertFalse;

public class ParticipantsStepdefs extends SpringAcceptanceTest{

    @When("^POST /participants is called for user \"([^\"]*)\"$")
    public void postParticipantsIsCalledForUser(String arg0) throws Throwable {
        assertFalse(false);
    }

    @Then("^Return code is \"([^\"]*)\"$")
    public void returnCodeIs(String arg0) throws Throwable {
        assertFalse(false);
    }

    @And("^when user \"([^\"]*)\" is looked up, it should exist$")
    public void whenUserIsLookedUpItShouldExist(String arg0) throws Throwable {
        assertFalse(false);
    }


    @Given("^User \"([^\"]*)\" exists in Mojaloop System$")
    public void userExistsInMojaloopSystem(String arg0) throws Throwable {
        assertFalse(false);
    }

    @When("^GET /participants is called for user \"([^\"]*)\"$")
    public void getParticipantsIsCalledForUser(String arg0) throws Throwable {
        assertFalse(false);
    }

    @Then("^DFSP \"([^\"]*)\" should be returned in the response$")
    public void dfspShouldBeReturnedInTheResponse(String arg0) throws Throwable {
        assertFalse(false);
    }
}

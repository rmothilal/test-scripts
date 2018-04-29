package stepdefs.user_onboarding;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class UserLookupStepdefs {
    @Given("^a user with valid \"([^\"]*)\" exists in central directory$")
    public void aUserWithValidExistsInCentralDirectory(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I look-up for \"([^\"]*)\"$")
    public void iLookUpFor(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I shoud get \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" back$")
    public void iShoudGetBack(String arg0, String arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^a user does not exist in central directory with a valid \"([^\"]*)\"$")
    public void aUserDoesNotExistInCentralDirectoryWithAValid(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I shoud get \"([^\"]*)\" back$")
    public void iShoudGetBack(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^a user with invalid \"([^\"]*)\"$")
    public void aUserWithInvalid(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I look-up for that invalid \"([^\"]*)\"$")
    public void iLookUpForThatInvalid(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I should get an error msg \"([^\"]*)\" back$")
    public void iShouldGetAnErrorMsgBack(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}

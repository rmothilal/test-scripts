# Mojaloop Functional Testing

This repository holds the test cases for the different business features offered by Mojaloop.
The framework chosen to implement this automated testing is Cucumber and using Java as the underlying implementation language. 
The below section gives a brief overview on how Cucumber is used in this testing.

The 2 main files that you will see across these tests are 
 - Gherkin Features
 - Step Definitions.
 
**Gherkin Features:**

This is the first step where the business features that are provided by Mojaloop Application are 
captured in a language called Gherkin. The recommended approach is to create a separate Gherkin file per feature.
Each Gherkin feature file will be further having more scenarios describing different variations of the feature. 
Each scenario takes the form of a 

    Given - Provided these conditions exist
    When - an action is taken
    Then - these results should be expected
    
Illustrating the usage of a Gherkin scenario using a sample business feature, User lookup, in Mojaloop.

    Feature: User Lookup
        As a payer FSP
        I should be able to lookup for a receiver MSISDN
        So that I can proceed further in P2P Money Transfer
        
    Scenario: 
        Given the receiver is registered in the central lookup application
        When I do a lookup based on receiver MSISDN
        Then the switch should provide receiver details like name, DOB etc..
        

**Step Definitions:**

This is the second step where you provide the test case implementation of the Gherkin Features. For Mojaloop, we are using Cucumber-Java for this implementation. Without going into the 
finer details of Cucumber implementation, as a tester you will define logic for the Given/When/Then in 
the StepDefinition file. 

Using the above Gherkin scenario example, the below snipped of code from the StepDefinition illustrates the usage:

_UserLookupStepDefs.java_
```Java
@Given("^the receiver is registered in the central lookup application$")
public void receiverIsRegistered() throws Throwable {
    //Logic
}

@When("^I do a lookup based on receiver MSISDN \"([^"]*)\"$")
public void receiverLookup(String fsp, String msisdn, String fullName, String firstName, String lastName, String dob) throws Throwable {
    //Logic
}

@Then("^the switch should provide receiver details like name, DOB etc.$")
public void receiverDetails(String expectedName, String expectedDOB) throws Throwable {
    //Logic
}
```

Given this brief overview about Gherkin and how it is impleemnted in Cucumber, the following sections talk about Installing the required software to run these tests, Steps to run the tests and an overview of the reports that will be generated.
 
Contents:

- [Required Software](#required software)
- [Setup Overview](#setup overview)
- [Running Tests](#running tests)
- [Reports](#reports)

## Required Software

1. JDK 1.8 or later
2. Apache Maven
3. Any IDE (Steps are included for Intellij and Eclipse)
4. ngrok (Optional. If you plan to run test cases from laptop)

## Setup Overview
 ![Sequence](SequenceDiagram.jpg)




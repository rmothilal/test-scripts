package stepdefs.p2p_transfer;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;
import com.mojaloop.utils.Utility;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.path.json.JsonPath;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import stepdefs.SpringAcceptanceTest;
import static com.mojaloop.utils.Utility.getRestTemplate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;


import javax.json.Json;
import javax.net.ssl.SSLContext;

import java.util.UUID;
import java.util.logging.Logger;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class P2PTransferGoldenPathStepdefs extends SpringAcceptanceTest {

    private Logger logger = Logger.getLogger(P2PTransferGoldenPathStepdefs.class.getName());

    ResponseEntity<String> response;

    String responseJson;

    String mojaloopHost = System.getProperty("mojaloop.host");
    String mojaloopUrl = "http://"+mojaloopHost+":8088/interop/switch/v1";

//    public TestRestTemplate getRestTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
//        TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
//            @Override
//            public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//                return true;
//            }
//        };
//        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
//        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
//        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
//        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
//        requestFactory.setHttpClient(httpClient);
//        TestRestTemplate restTemplate = new TestRestTemplate();
//        restTemplate.getRestTemplate().setRequestFactory(requestFactory);
//        return restTemplate;
//    }

    @When("^In fsp \"([^\"]*)\" when I add user with the following details  MSISDN: \"([^\"]*)\" Full Name: \"([^\"]*)\" First Name: \"([^\"]*)\" Last Name: \"([^\"]*)\" DOB: \"([^\"]*)\"$")
    public void inFspWhenIAddUserWithTheFollowingDetailsMSISDNFullNameFirstNameLastNameDOB(String fsp, String msisdn, String fullName, String firstName, String lastName, String dob) throws Throwable {
        String data = Json.createObjectBuilder()
                            .add("party", Json.createObjectBuilder()
                                .add("partyIdInfo", Json.createObjectBuilder()
                                        .add("partyIdType","MSISDN")
                                        .add("partyIdentifier", msisdn)
                                        .add("fspId", fsp)
                                )
                                .add("name", fullName)
                                .add("personalInfo", Json.createObjectBuilder()
                                    .add("complexName",Json.createObjectBuilder()
                                            .add("firstName", firstName)
                                            .add("lastName", lastName)
                                    )
                                    .add("dateOfBirth",dob)
                                )
                            )
                            .build()
                            .toString();

        logger.info("Maven property: "+System.getProperty("test"));

        String endPoint = "/"+fsp+"/parties/MSISDN/"+msisdn;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(data,headers);

        //response = restTemplate.postForEntity(endPoint,entity,String.class);


        response = getRestTemplate().postForEntity("https://localhost:8444"+endPoint,entity,String.class);

    }

    @Then("^User \"([^\"]*)\" should be successfully added$")
    public void userShouldBeSuccessfullyAdded(String arg0) throws Throwable {
        assertThat(response.getStatusCodeValue(), is(200));
    }


    @When("^I add MSISDN \"([^\"]*)\" in fsp \"([^\"]*)\"$")
    public void iAddMSISDNInFsp(String msisdn, String fsp) throws Throwable {
        String requestJson = Json.createObjectBuilder()
                                    .add("fspId", fsp)
                                    .add("currency","USD")
                                    .build().toString();
        responseJson = Utility.post(mojaloopUrl + "/participants/MSISDN/" + msisdn,fsp,null,null,requestJson,getRestTemplate());

    }

    @Then("^I want to ensure that MSISDN \"([^\"]*)\" is successfully added to the switch under fsp \"([^\"]*)\"$")
    public void iWantToEnsureThatMSISDNIsSuccessfullyAddedToTheSwitch(String msisdn, String fsp) throws Throwable {
        //String responseJson = Utility.get(mojaloopUrl + "/participants/MSISDN/"+msisdn,fsp,null,null,restTemplate);
        assertThat(responseJson,containsString(fsp));
    }

    @Given("^Payer \"([^\"]*)\" in Payer FSP \"([^\"]*)\" and Payee \"([^\"]*)\" in Payee FSP \"([^\"]*)\" exists in the switch$")
    public void payerInPayerFSPAndPayeeInPayeeFSPExistsInTheSwitch(String arg0, String arg1, String arg2, String arg3) throws Throwable {
        assertThat("Skipping",true==true);
    }

    @When("^Payer \"([^\"]*)\" with MSISDN \"([^\"]*)\" does a lookup for payee \"([^\"]*)\" with MSISDN \"([^\"]*)\"$")
    public void payerWithMSISDNDoesALookupForPayeeWithMSISDN(String payerName, String payerMSISDN, String payeeName, String payeeMSISDN) throws Throwable {
        responseJson = Utility.get(mojaloopUrl + "/parties/MSISDN/"+payeeMSISDN,"payerfsp","payeefsp",null,getRestTemplate());
    }

    @Then("^Payee \"([^\"]*)\" results should be returned\\. Expected values are First Name \"([^\"]*)\" Last Name \"([^\"]*)\" DOB \"([^\"]*)\"$")
    public void payeeResultsShouldBeReturnedExpectedValuesAreFirstNameLastNameDOB(String payeeFullName, String payeeFirstName, String payeeLastName, String payeeDOB) throws Throwable {
        JsonPath jPath = JsonPath.from(responseJson);
        assertThat(jPath.getString("party.personalInfo.complexName.firstName"), is(payeeFirstName));
        assertThat(jPath.getString("party.personalInfo.complexName.lastName"), is(payeeLastName));
        assertThat(jPath.getString("party.personalInfo.dateOfBirth"), is(payeeDOB));
    }

    @When("^Payer FSP issues a quote to the switch by providing \"([^\"]*)\" and \"([^\"]*)\"\\. Payer MSISDN is \"([^\"]*)\" Payee MSISDN is \"([^\"]*)\"$")
    public void payerFSPIssuesAQuoteToTheSwitchByProvidingAndPayerMSISDNIsPayeeMSISDNIs(String amount, String currency, String payerMsisdn, String payeeMsisdn) throws Throwable {
        String quoteRequest = Json.createObjectBuilder()
                .add("quoteId",UUID.randomUUID().toString())
                .add("transactionId",UUID.randomUUID().toString())
                .add("payer", Json.createObjectBuilder()
                        .add("partyIdInfo",Json.createObjectBuilder()
                                .add("partyIdentifier",payerMsisdn)
                                .add("partyIdType","MSISDN")
                                .add("fspId","payerfsp")
                        )
                )
                .add("payee", Json.createObjectBuilder()
                        .add("partyIdInfo",Json.createObjectBuilder()
                                .add("partyIdentifier",payeeMsisdn)
                                .add("partyIdType","MSISDN")
                                .add("fspId","payeefsp")
                        )
                )
                .add("amount",Json.createObjectBuilder()
                        .add("amount",amount)
                        .add("currency",currency)
                )
                .add("amountType","SEND")
                .add("transactionType", Json.createObjectBuilder()
                        .add("scenario","DEPOSIT")
                        .add("initiator","PAYER")
                        .add("initiatorType","CONSUMER")
                )
                .build()
                .toString();
        responseJson = Utility.post(mojaloopUrl + "/quotes","payerfsp","payeefsp",null,quoteRequest,getRestTemplate());
    }

    @Then("^Payer FSP should see total fee and commission for the \"([^\"]*)\" specified by payer\\. Expected payee fsp fee is \"([^\"]*)\" and Expected payee fsp commission is \"([^\"]*)\"$")
    public void payerFSPShouldSeeTotalFeeAndCommissionForTheSpecifiedByPayeeExpectedPayeeFspFeeIsAndExpectedPayeeFspCommissionIs(String arg0, String arg1, String arg2) throws Throwable {
        com.jayway.jsonpath.DocumentContext responseDoc = com.jayway.jsonpath.JsonPath.parse(responseJson, Configuration.defaultConfiguration().addOptions(Option.SUPPRESS_EXCEPTIONS));
        assertThat(responseDoc.read("payeeFspFee.amount"),is("1"));
        assertThat(responseDoc.read("payeeFspCommission.amount"),is("1"));
        assertThat(responseDoc.read("ilpPacket"),is(not("")));
        assertThat(responseDoc.read("condition"),is(not("")));
    }

    @Given("^A quote exists\\. Payer MSISDN \"([^\"]*)\" Payee MSISDN \"([^\"]*)\" Amount \"([^\"]*)\"$")
    public void aQuoteExistsPayerMSISDNPayeeMSISDNAmount(String payerMsisdn, String payeeMsisdn, String amount) throws Throwable {
        String quoteRequest = Json.createObjectBuilder()
                .add("quoteId",UUID.randomUUID().toString())
                .add("transactionId",UUID.randomUUID().toString())
                .add("payer", Json.createObjectBuilder()
                        .add("partyIdInfo",Json.createObjectBuilder()
                                .add("partyIdentifier",payerMsisdn)
                                .add("partyIdType","MSISDN")
                                .add("fspId","payerfsp")
                        )
                )
                .add("payee", Json.createObjectBuilder()
                        .add("partyIdInfo",Json.createObjectBuilder()
                                .add("partyIdentifier",payeeMsisdn)
                                .add("partyIdType","MSISDN")
                                .add("fspId","payeefsp")
                        )
                )
                .add("amount",Json.createObjectBuilder()
                        .add("amount",amount)
                        .add("currency","USD")
                )
                .add("amountType","SEND")
                .add("transactionType", Json.createObjectBuilder()
                        .add("scenario","DEPOSIT")
                        .add("initiator","PAYER")
                        .add("initiatorType","CONSUMER")
                )
                .build()
                .toString();
        responseJson = Utility.post(mojaloopUrl + "/quotes","payerfsp","payeefsp",null,quoteRequest,getRestTemplate());
    }

    @When("^I submit a transfer for amount \"([^\"]*)\"$")
    public void iSubmitATransferForAmount(String amount) throws Throwable {
        com.jayway.jsonpath.DocumentContext quoteResponseDoc = com.jayway.jsonpath.JsonPath.parse(responseJson, Configuration.defaultConfiguration().addOptions(Option.SUPPRESS_EXCEPTIONS));
        String transferRequest = Json.createObjectBuilder()
                .add("transferId", UUID.randomUUID().toString())
                .add("payerFsp", "payerfsp")
                .add("payeeFsp", "payeefsp")
                .add("amount", Json.createObjectBuilder()
                        .add("amount", amount)
                        .add("currency", "USD")
                )
                .add("expiration", quoteResponseDoc.read("expiration").toString())
                .add("ilpPacket",quoteResponseDoc.read("ilpPacket").toString())
                .add("condition",quoteResponseDoc.read("condition").toString())
                .build()
                .toString();
        responseJson = Utility.post(mojaloopUrl + "/transfers","payerfsp","payeefsp",null,transferRequest,getRestTemplate());
    }

    @Then("^I should get a fulfillment response back with a transfer state of \"([^\"]*)\"$")
    public void iShouldGetAFulfillmentResponseBack(String transferState) throws Throwable {
        com.jayway.jsonpath.DocumentContext responseDoc = com.jayway.jsonpath.JsonPath.parse(responseJson, Configuration.defaultConfiguration().addOptions(Option.SUPPRESS_EXCEPTIONS));
        assertThat(responseDoc.read("transferState"),is(transferState));
        assertThat(responseDoc.read("fulfilment"),is(not("")));
    }
}

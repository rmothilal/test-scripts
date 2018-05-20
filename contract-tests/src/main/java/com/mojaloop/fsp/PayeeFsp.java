package com.mojaloop.fsp;

import com.ilp.conditions.impl.IlpConditionHandlerImpl;
import com.ilp.conditions.models.pdp.*;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;
import io.restassured.path.json.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.jms.Queue;
import javax.json.Json;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Logger;

@RestController
@RequestMapping("/payeefsp")
public class PayeeFsp {


    private Logger logger = Logger.getLogger(com.mojaloop.fsp.PayeeFsp.class.getName());

    private HashMap<String, String> entityMap = new HashMap<String, String>();

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue partiesQueue;

    @Autowired
    private Queue quotesQueue;

    @Autowired
    private Queue transfersQueue;

    @Value("${mojaloop.host}")
    private String mojaloopHost;

    @Value("${mojaloop.port}")
    private String mojaloopPort;

    @RequestMapping(method = {RequestMethod.GET}, value = {"/version"})
    public String getVersion() {
        return "1.0";
    }

    @RequestMapping(value = "/participants/{Type}/{Id}",method = RequestMethod.PUT)
    public HttpStatus putParticipants(@PathVariable("Type") String type, @PathVariable("Id") String id, @RequestHeader("X-Forwarded-For") String correlationId, @RequestBody String payload) throws IOException {
        logger.info("Header: " + correlationId.substring(0, correlationId.indexOf(",")) + " Body: " + payload);
        if(correlationId.indexOf(",") != -1) {
            logger.info("Header: " + correlationId.substring(0, correlationId.indexOf(",")) + " Body: " + payload);
            entityMap.put(correlationId.substring(0, correlationId.indexOf(",")), payload);
        }
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/participants/{Type}/{Id}/error",method = RequestMethod.PUT)
    public void putParticipantsError(@PathVariable("Type") String type, @PathVariable("Id") String id, @RequestHeader("X-Forwarded-For") String correlationId, @RequestBody String payload) throws IOException {
        if(correlationId.indexOf(",") != -1) {
            logger.info("Header: " + correlationId.substring(0, correlationId.indexOf(",")) + " Body: " + payload);
            entityMap.put(correlationId.substring(0, correlationId.indexOf(",")), payload);
        }
    }

    @RequestMapping(value = "/parties/{Type}/{Id}", method = RequestMethod.POST)
    public String postParties(@PathVariable("Type") String type, @PathVariable("Id") String id, @RequestBody String payload) throws IOException {
        entityMap.put(id, payload);
        return "200";
    }

    @RequestMapping(value = "/parties/{Type}/{phNum}", method = RequestMethod.GET)
    public HttpStatus getParties(@PathVariable("Type") String type, @PathVariable("phNum") String phNum, @RequestHeader HttpHeaders httpHeaders) throws IOException {
        HashMap<String,Object> jmsHeaders = new HashMap<String, Object>();
        populateJMSHeaders(httpHeaders,jmsHeaders);
        jmsHeaders.put("type",type);
        jmsHeaders.put("phNum",phNum);

        this.jmsMessagingTemplate.convertAndSend(this.partiesQueue, "parties request", jmsHeaders);

        return HttpStatus.ACCEPTED;
    }

    @JmsListener(destination = "parties.queue")
    public void receivePartiesQueue(Message message) {
        MessageHeaders messageHeaders = message.getHeaders();

        //Looking up for the payee based on the input MSISDN
        String payee = entityMap.get(messageHeaders.get("phNum"));
        logger.info("In PayeeFSP. Payee details: "+payee);

        String endpoint = "http://"+mojaloopHost+":"+mojaloopPort+"/interop/switch/v1/parties/"+messageHeaders.get("type")+"/"+messageHeaders.get("phNum");
        logger.info("Endpoint: "+endpoint);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        populateHTTPHeaders(messageHeaders, httpHeaders);
        HttpEntity entity = new HttpEntity(payee,httpHeaders);

        restTemplate.exchange(endpoint,HttpMethod.PUT,entity,String.class);
    }

    @RequestMapping(value = "/quotes", method = RequestMethod.POST)
    public HttpStatus postQuotes( @RequestBody String payload, @RequestHeader HttpHeaders httpHeaders) throws IOException {
        HashMap<String,Object> jmsHeaders = new HashMap<String, Object>();
        populateJMSHeaders(httpHeaders,jmsHeaders);

        this.jmsMessagingTemplate.convertAndSend(this.quotesQueue, payload, jmsHeaders);

        return HttpStatus.ACCEPTED;
    }

    @JmsListener(destination = "quotes.queue")
    public void receiveQuotesQueue(Message message) throws IOException {
        MessageHeaders messageHeaders = message.getHeaders();


        com.jayway.jsonpath.DocumentContext cxtOriginalMojaloopQuotesRequest = com.jayway.jsonpath.JsonPath.parse(message.getPayload().toString(), Configuration.defaultConfiguration().addOptions(Option.SUPPRESS_EXCEPTIONS));
        String amountStr = cxtOriginalMojaloopQuotesRequest.read("amount.amount");
        //String ilpAddress = "private.".concat(fspId);
        String ilpAddress = "private.".concat("payeefsp");
        long amount = (long) Double.parseDouble(amountStr);

        Transaction transaction = populateTransactionWithQuote(cxtOriginalMojaloopQuotesRequest);

        logger.info("IlpAddress: " + ilpAddress + " Amount: " + amount + " and Transaction: " + transaction.toString());

        //Call interop-ilp-conditions jar getIlpPacket()
        IlpConditionHandlerImpl ilpConditionHandlerImpl = new IlpConditionHandlerImpl();
        String ilpPacket = ilpConditionHandlerImpl.getILPPacket(ilpAddress, amount, transaction);

        //Call interop-ilp-conditions jar generateCondition()
        byte[] secret = "secret".getBytes();
        String ilpCondition = ilpConditionHandlerImpl.generateCondition(ilpPacket, secret);

        //Calculate Expiration time
        Date expiryDt = org.apache.commons.lang3.time.DateUtils.addHours(new Date(),10);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        String response =  Json.createObjectBuilder()
                .add("transferAmount", Json.createObjectBuilder()
                        .add("amount", cxtOriginalMojaloopQuotesRequest.read("amount.amount").toString())
                        .add("currency", cxtOriginalMojaloopQuotesRequest.read("amount.currency").toString())
                )
                .add("payeeFspFee", Json.createObjectBuilder()
                        .add("amount", "1")
                        .add("currency", "USD")
                )
                .add("payeeFspCommission", Json.createObjectBuilder()
                        .add("amount", "1")
                        .add("currency", "USD")
                )
                .add("expiration", sdf.format(expiryDt))
                .add("ilpPacket", ilpPacket)
                .add("condition", ilpCondition)
                .build()
                .toString();

        String quoteId = cxtOriginalMojaloopQuotesRequest.read("quoteId").toString();
        String endpoint = "http://"+mojaloopHost+":"+mojaloopPort+"/interop/switch/v1/quotes/"+quoteId;
        logger.info("Endpoint: "+endpoint);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        populateHTTPHeaders(messageHeaders, httpHeaders);

        HttpEntity entity = new HttpEntity(response,httpHeaders);


        restTemplate.exchange(endpoint,HttpMethod.PUT,entity,String.class);
    }

    @RequestMapping(value = "/transfers", method = RequestMethod.POST)
    public HttpStatus postTransfers( @RequestBody String payload, @RequestHeader HttpHeaders httpHeaders) throws IOException {
        HashMap<String,Object> jmsHeaders = new HashMap<String, Object>();
        populateJMSHeaders(httpHeaders,jmsHeaders);
        logger.info("In PAYEE FSP TRANSFERS: "+payload);
        this.jmsMessagingTemplate.convertAndSend(this.transfersQueue, payload, jmsHeaders);

        return HttpStatus.ACCEPTED;
    }

    @JmsListener(destination = "transfers.queue")
    public void receiveTransfersQueue(Message message) throws IOException {
        MessageHeaders messageHeaders = message.getHeaders();


        JsonPath jPathOriginalMojaloopTransferRequest = JsonPath.from(message.getPayload().toString());
        String ilpPacket = jPathOriginalMojaloopTransferRequest.getString("ilpPacket");
        IlpConditionHandlerImpl ilpConditionHandlerImpl = new IlpConditionHandlerImpl();
        String rawFulfillment = ilpConditionHandlerImpl.generateFulfillment(ilpPacket,"secret".getBytes());

        String response =  Json.createObjectBuilder()
                .add("fulfilment",rawFulfillment)
                .add("transferState","COMMITTED")
                .build()
                .toString();

        String transferId = jPathOriginalMojaloopTransferRequest.getString("transferId");
        String endpoint = "http://"+mojaloopHost+":"+mojaloopPort+"/interop/switch/v1/transfers/"+transferId;
        logger.info("Endpoint: "+endpoint);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        populateHTTPHeaders(messageHeaders, httpHeaders);

        HttpEntity entity = new HttpEntity(response,httpHeaders);


        restTemplate.exchange(endpoint,HttpMethod.PUT,entity,String.class);
    }



    @RequestMapping(value = "/correlationid", method = RequestMethod.POST)
    public void addCorrelationId(@RequestBody String payload){
        JsonParser jsonParser = new JacksonJsonParser();
        entityMap.put((String)jsonParser.parseMap(payload).get("correlationId"),(String)jsonParser.parseMap(payload).get("correlationId"));
    }

    @RequestMapping(value = "/correlationid/{correlationId}", method = RequestMethod.GET)
    public String getCorrelationId(@PathVariable String correlationId){
        logger.info("correlationId in getCorrelationId: "+correlationId);
        return entityMap.get(correlationId);
    }

    public void populateJMSHeaders(HttpHeaders httpHeaders, HashMap<String,Object> jmsHeaders){
        if(httpHeaders.get("FSPIOP-Source") != null && httpHeaders.get("FSPIOP-Source").get(0) != null)
            jmsHeaders.put("FSPIOP-Source", (String)httpHeaders.get("FSPIOP-Source").get(0));
        if(httpHeaders.get("FSPIOP-Destination") != null && httpHeaders.get("FSPIOP-Destination").get(0) != null)
            jmsHeaders.put("FSPIOP-Destination", (String)httpHeaders.get("FSPIOP-Destination").get(0));
        if(httpHeaders.get("X-Forwarded-For") != null && httpHeaders.get("X-Forwarded-For").get(0) != null)
            jmsHeaders.put("X-Forwarded-For", (String)httpHeaders.get("X-Forwarded-For").get(0));
    }

    public void populateHTTPHeaders(MessageHeaders jmsHeaders, HttpHeaders httpHeaders){
        //Source and Destination are swapped while going back
        if(jmsHeaders.get("FSPIOP-Source") != null )
            httpHeaders.add("FSPIOP-Destination", (String)jmsHeaders.get("FSPIOP-Source"));
        if(jmsHeaders.get("FSPIOP-Destination") != null )
            httpHeaders.add("FSPIOP-Source", (String)jmsHeaders.get("FSPIOP-Destination"));
        if(jmsHeaders.get("X-Forwarded-For") != null )
            httpHeaders.add("X-Forwarded-For", (String)jmsHeaders.get("X-Forwarded-For"));
    }

    private static Transaction populateTransactionWithQuote(com.jayway.jsonpath.DocumentContext cxtOriginalMojaloopQuotesRequest) {

        Transaction transaction = new Transaction();

        transaction.setTransactionId(cxtOriginalMojaloopQuotesRequest.read("transactionId"));
        transaction.setQuoteId(cxtOriginalMojaloopQuotesRequest.read("quoteId"));

        //Populating Payer Info
        Party payer = new Party();
        payer.setMerchantClassificationCode(cxtOriginalMojaloopQuotesRequest.read("payer.merchantClassificationCode"));
        payer.setName(cxtOriginalMojaloopQuotesRequest.read("payer.name"));

        PartyIdInfo payerIdInfo = new PartyIdInfo();
        payerIdInfo.setPartyIdentifier(cxtOriginalMojaloopQuotesRequest.read("payer.partyIdInfo.partyIdentifier"));
        if(cxtOriginalMojaloopQuotesRequest.read("payer.partyIdInfo.partyIdType")!=null)
            payerIdInfo.setPartyIdType(cxtOriginalMojaloopQuotesRequest.read("payer.partyIdInfo.partyIdType"));
        else
            payerIdInfo.setPartyIdType("MSISDN");
        payerIdInfo.setPartySubIdOrType(cxtOriginalMojaloopQuotesRequest.read("payer.partyIdInfo.partySubIdOrType"));
        payerIdInfo.setFspId(cxtOriginalMojaloopQuotesRequest.read("payer.partyIdInfo.fspId"));
        payer.setPartyIdInfo(payerIdInfo);

        PartyPersonalInfo payerPersonalInfo  = new PartyPersonalInfo();
        PartyComplexName payerComplexName =  new PartyComplexName();
        payerComplexName.setFirstName(cxtOriginalMojaloopQuotesRequest.read("payer.personalInfo.complexName.firstName"));
        payerComplexName.setLastName(cxtOriginalMojaloopQuotesRequest.read("payer.personalInfo.complexName.lastName"));
        payerComplexName.setMiddleName(cxtOriginalMojaloopQuotesRequest.read("payer.personalInfo.complexName.middleName"));//TODO: Check for empty
        payerPersonalInfo.setComplexName(payerComplexName);
        payer.setPersonalInfo(payerPersonalInfo);
        transaction.setPayer(payer);

        //Populating Payee Info
        Party payee = new Party();
        payee.setMerchantClassificationCode(cxtOriginalMojaloopQuotesRequest.read("payee.merchantClassificationCode"));
        payee.setName(cxtOriginalMojaloopQuotesRequest.read("payee.name"));

        PartyIdInfo payeeIdInfo = new PartyIdInfo();
        payeeIdInfo.setPartyIdentifier(cxtOriginalMojaloopQuotesRequest.read("payee.partyIdInfo.partyIdentifier"));
        if(cxtOriginalMojaloopQuotesRequest.read("payee.partyIdInfo.partyIdType")!=null)
            payeeIdInfo.setPartyIdType(cxtOriginalMojaloopQuotesRequest.read("payee.partyIdInfo.partyIdType"));
        else
            payeeIdInfo.setPartyIdType("MSISDN");
        payeeIdInfo.setPartySubIdOrType(cxtOriginalMojaloopQuotesRequest.read("payee.partyIdInfo.partySubIdOrType"));
        payeeIdInfo.setFspId(cxtOriginalMojaloopQuotesRequest.read("payee.partyIdInfo.fspId"));
        payee.setPartyIdInfo(payeeIdInfo);

        PartyPersonalInfo payeePersonalInfo  = new PartyPersonalInfo();
        PartyComplexName payeeComplexName =  new PartyComplexName();
        payeeComplexName.setFirstName(cxtOriginalMojaloopQuotesRequest.read("payee.personalInfo.complexName.firstName"));
        payeeComplexName.setLastName(cxtOriginalMojaloopQuotesRequest.read("payee.personalInfo.complexName.lastName"));
        payeeComplexName.setMiddleName(cxtOriginalMojaloopQuotesRequest.read("payee.personalInfo.complexName.middleName"));
        payeePersonalInfo.setComplexName(payeeComplexName);
        payee.setPersonalInfo(payeePersonalInfo);
        transaction.setPayee(payee);

        Money m = new Money();
        m.setAmount(cxtOriginalMojaloopQuotesRequest.read("amount.amount"));
        m.setCurrency(cxtOriginalMojaloopQuotesRequest.read("amount.currency"));
        transaction.setAmount(m);

        TransactionType tt = new TransactionType();
        tt.setBalanceOfPayments(cxtOriginalMojaloopQuotesRequest.read("transactionType.balanceOfPayments"));
        tt.setInitiator(cxtOriginalMojaloopQuotesRequest.read("transactionType.initiator"));
        tt.setInitiatorType(cxtOriginalMojaloopQuotesRequest.read("transactionType.initiatorType"));
        Refund ri = new Refund();
        ri.setOriginalTransactionId(cxtOriginalMojaloopQuotesRequest.read("transactionType.refundInfo.originalTransactionId"));
        ri.setRefundReason(cxtOriginalMojaloopQuotesRequest.read("transactionType.refundInfo.refundReason"));
        tt.setRefundInfo(ri);
        tt.setScenario(cxtOriginalMojaloopQuotesRequest.read("transactionType.scenario"));
        tt.setSubScenario(cxtOriginalMojaloopQuotesRequest.read("transactionType.scenario"));
        transaction.setTransactionType(tt);

        transaction.setNote(cxtOriginalMojaloopQuotesRequest.read("note"));

//        ExtensionList el = new ExtensionList();
//        Extension e1 = new Extension();
//        e1.setKey(jPathOriginalMojaloopQuotesRequest.getString("extensionList[0].key"));
//        e1.setValue(jPathOriginalMojaloopQuotesRequest.getString("extensionList[0].value"));
//        Extension e2 = new Extension();
//        e2.setKey(jPathOriginalMojaloopQuotesRequest.getString("extensionList[1].key"));
//        e2.setValue(jPathOriginalMojaloopQuotesRequest.getString("extensionList[1].value"));
//        List<Extension> le = new ArrayList<>();
//        le.add(e1);
//        le.add(e2);
//        el.setExtension(le);
//        transaction.setExtensionList(el);

        return transaction;
    }
}

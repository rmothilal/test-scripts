package com.mojaloop.fsp;

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
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.jms.Queue;
import java.io.IOException;
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
}

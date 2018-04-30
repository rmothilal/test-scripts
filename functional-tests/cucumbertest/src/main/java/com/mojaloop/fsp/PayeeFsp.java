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
    private String mohaloopPort;

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

    @RequestMapping(value = "/parties/{Type}/{Id}", method = RequestMethod.GET)
    public HttpStatus getParties(@PathVariable("Type") String type, @PathVariable("Id") String id) throws IOException {
        this.jmsMessagingTemplate.convertAndSend(this.partiesQueue, "parties request");
        return HttpStatus.ACCEPTED;
    }

    @JmsListener(destination = "parties.queue")
    public void receiveQueue(String text) {
        logger.info("TESTING: "+text);
        String endpoint = "http://"+mojaloopHost+":"+mohaloopPort+"/interop/switch/v1/parties/MSISDN"
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("FSPIOP-Source", "payeefsp");
        headers.set("FSPIOP-Destination", "payerfsp");
        HttpEntity entity = new HttpEntity(headers);
        restTemplate.exchange("",HttpMethod.PUT,entity,String.class);
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
}

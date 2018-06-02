/*****
 License
 --------------
 Copyright © 2017 Bill & Melinda Gates Foundation
 The Mojaloop files are made available by the Bill & Melinda Gates Foundation under the Apache License, Version 2.0 (the "License") and you may not use these files except in compliance with the License. You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing, the Mojaloop files are distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 Contributors
 --------------
 This is the official list of the Mojaloop project contributors for this file.
 Names of the original copyright holders (individuals or organizations)
 should be listed with a '*' in the first column. People who have
 contributed from an organization can be listed under the organization
 that actually holds the copyright for their contributions (see the
 Gates Foundation organization for an example). Those individuals should have
 their names indented and be marked with a '-'. Email address can be added
 optionally within square brackets <email>.
 * Gates Foundation
 - Name Murthy Kakarlamudi <murthy@modusbox.com>
 --------------
 ******/

package com.mojaloop.fsp;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

@RestController
@RequestMapping("/payerfsp")
public class PayerFsp {

    private Logger logger = Logger.getLogger(PayerFsp.class.getName());

    private HashMap<String,String> entityMap = new HashMap<String,String>();

    @RequestMapping(method = { RequestMethod.GET }, value = { "/version" })
    public String getVersion() {
        return "1.0";
    }

    @RequestMapping(value = "/participants/{Type}/{Id}",method = RequestMethod.PUT)
    public HttpStatus putParticipants(@PathVariable("Type") String type, @PathVariable("Id") String id, @RequestHeader("X-Forwarded-For") String correlationId, @RequestBody String payload) throws IOException {
        logger.info("Header: " + correlationId + " Body: " + payload);
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

    @RequestMapping(value = "/parties/{Type}/{Id}",method = RequestMethod.POST)
    public String postParties(@PathVariable("Type") String type, @PathVariable("Id") String id, @RequestBody String payload) throws IOException {
        logger.info("Entered in payerfsp");
        entityMap.put(id,payload);
        return "200";
    }

    @RequestMapping(value = "/parties/{Type}/{Id}",method = RequestMethod.PUT)
    public HttpStatus putParties(@PathVariable("Type") String type, @PathVariable("Id") String id, @RequestHeader("X-Forwarded-For") String correlationId, @RequestBody String payload) throws IOException {
        logger.info("Header: " + correlationId + " Body: " + payload);
        if(correlationId.indexOf(",") != -1) {
            logger.info("Header: " + correlationId.substring(0, correlationId.indexOf(",")) + " Body: " + payload);
            entityMap.put(correlationId.substring(0, correlationId.indexOf(",")), payload);
        }
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/parties/{Type}/{Id}/error",method = RequestMethod.PUT)
    public void putPartiesError(@PathVariable("Type") String type, @PathVariable("Id") String id, @RequestHeader("X-Forwarded-For") String correlationId, @RequestBody String payload) throws IOException {
        if(correlationId.indexOf(",") != -1) {
            logger.info("Header: " + correlationId.substring(0, correlationId.indexOf(",")) + " Body: " + payload);
            entityMap.put(correlationId.substring(0, correlationId.indexOf(",")), payload);
        }
    }

    @RequestMapping(value = "/quotes/{quoteId}",method = RequestMethod.PUT)
    public HttpStatus putQuotes(@PathVariable("quoteId") String quoteId, @RequestHeader("X-Forwarded-For") String correlationId, @RequestBody String payload) throws IOException {
        logger.info("Header: " + correlationId + " Body: " + payload);
        if(correlationId.indexOf(",") != -1) {
            logger.info("Header: " + correlationId.substring(0, correlationId.indexOf(",")) + " Body: " + payload);
            entityMap.put(correlationId.substring(0, correlationId.indexOf(",")), payload);
        }
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/quotes/{quoteId}/error",method = RequestMethod.PUT)
    public void putQuotesError(@PathVariable("quoteId") String quoteId, @RequestHeader("X-Forwarded-For") String correlationId, @RequestBody String payload) throws IOException {
        if(correlationId.indexOf(",") != -1) {
            logger.info("Header: " + correlationId.substring(0, correlationId.indexOf(",")) + " Body: " + payload);
            entityMap.put(correlationId.substring(0, correlationId.indexOf(",")), payload);
        }
    }

    @RequestMapping(value = "/transfers/{transferId}",method = RequestMethod.PUT)
    public HttpStatus putTransfers(@PathVariable("transferId") String transferId, @RequestHeader("X-Forwarded-For") String correlationId, @RequestBody String payload) throws IOException {
        logger.info("Header: " + correlationId + " Body: " + payload);
        if(correlationId.indexOf(",") != -1) {
            logger.info("Header: " + correlationId.substring(0, correlationId.indexOf(",")) + " Body: " + payload);
            entityMap.put(correlationId.substring(0, correlationId.indexOf(",")), payload);
        }
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/transfers/{transferId}/error",method = RequestMethod.PUT)
    public void putTransfersError(@PathVariable("transferId") String transferId, @RequestHeader("X-Forwarded-For") String correlationId, @RequestBody String payload) throws IOException {
        if(correlationId.indexOf(",") != -1) {
            logger.info("Header: " + correlationId.substring(0, correlationId.indexOf(",")) + " Body: " + payload);
            entityMap.put(correlationId.substring(0, correlationId.indexOf(",")), payload);
        }
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

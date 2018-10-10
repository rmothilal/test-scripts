package pi3mock;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.time.Instant;

import com.ilp.conditions.impl.IlpConditionHandlerImpl;
import java.util.Base64;

import org.apache.http.protocol.HTTP;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;

@RestController
public class MockFSP {

    private static final String template = "Hello %s!";
    private final AtomicLong counter = new AtomicLong();
    private static final String PUT_URL = "http://localhost:4000/transfers/";
    private static final Logger logger = LoggerFactory.getLogger(Mock.class);

    @PutMapping(path = "/transfers/{id}")
    public String handlePut(@PathVariable String id, @RequestBody String putTransferRequest) {
        //logger.info("Request:" + putTransferRequest );

        JSONObject incomingMessageBody=new JSONObject(putTransferRequest);
        String transferId = incomingMessageBody.get("transferId").toString();

        try {
            logger.info("In PUT /transfers/{ID} for transferId:" + transferId);
            FileWriter fw = null;
            BufferedWriter bw = null;
            PrintWriter pw = null;

            fw = new FileWriter("CallbacksReceived.csv", true);
            bw = new BufferedWriter(fw); pw = new PrintWriter(bw);

            StringBuilder sb = new StringBuilder();
            sb.append(transferId); sb.append(',');
            sb.append(Calendar.getInstance().getTimeInMillis()); sb.append('\n');

            pw.print(sb.toString());
            pw.close(); bw.close(); fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "OK";
    }

    @PostMapping(path = "/transfers")
    public String handlePost(@RequestBody String postTransferRequest) {
        //logger.info("Request: " + postTransferRequest );

        JSONObject incomingMessageBody=new JSONObject(postTransferRequest);
        String transferId = incomingMessageBody.get("transferId").toString();
        String ilpPacket = incomingMessageBody.get("ilpPacket").toString();
        //Call interop-ilp-conditions jar getIlpPacket()
        IlpConditionHandlerImpl ilpConditionHandlerImpl = new IlpConditionHandlerImpl();
        //TODO: Obtain secret and replace hardcoded value
        byte[] secret = new byte[32];
        secret = Base64.getEncoder().encode("secret".getBytes());
        //Call interop-ilp-conditions jar generateFulfillment()
        String fulfillment = ilpConditionHandlerImpl.generateFulfillment(ilpPacket, secret);

        //Headers
        String dateHeader = java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now( ZoneId.of("GMT") ) );
        String acceptHeader = "1.0";
        String contentTypeHeader = "application/json";
        String sourceHeader = "dfsp2";
        String destinationHeader = "dfsp1";
        Instant timestamp = Instant.now();

        String Switch_URL = PUT_URL + transferId;

        // Fulfilment Message
        JSONObject putMessageBody=new JSONObject();
        putMessageBody.put("fulfilment", fulfillment);
        putMessageBody.put("transferState", "COMMITTED");
        putMessageBody.put("completedTimestamp", timestamp.toString() );
        logger.info("In POST /transfers"+ "\ntransferId: " + transferId + "\nilpPacket: " + ilpPacket + "\ngenerated fulfillment: " + fulfillment + "\ncompletedTimestamp: " + timestamp.toString() );

        try {
            Thread.sleep(1);

            HttpClient httpClient = HttpClientBuilder.create().build();
            logger.info("URL: " + Switch_URL);

            HttpPut putter=new HttpPut( Switch_URL );
            putter.setHeader("FSPIOP-Source",sourceHeader);
            putter.setHeader("FSPIOP-Destination",destinationHeader);
            putter.setHeader("Date", dateHeader);
            putter.setHeader("Accept",acceptHeader);
            putter.setHeader("Content-type", contentTypeHeader);

            StringEntity se = new StringEntity( putMessageBody.toString());
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, contentTypeHeader));
            putter.setEntity(se);

            HttpResponse response=httpClient.execute(putter);
            logger.info("Sent PUT /transfers, for transferId: " + transferId + " with response code: " + response.getStatusLine().getStatusCode());
        }
        catch (Exception e)
        {
            System.out.println("Exception :: " + e.getMessage() );
        }

        return "Accepted";
    }

    @GetMapping("/health")
    public Mock handleGet(@RequestParam(value="name", defaultValue="World") String name) {
        logger.info("In GET /health");
        return new Mock(counter.incrementAndGet(),
                String.format(template, name));
    }
}

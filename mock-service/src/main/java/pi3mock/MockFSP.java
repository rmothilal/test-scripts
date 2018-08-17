package pi3mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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

import java.net.URL;

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

        logger.info("In POST /transfers, transferId: " + transferId);

        String Switch_URL = PUT_URL + transferId;

        JSONObject putMessageBody=new JSONObject();
        putMessageBody.put("fulfilment", "f5sqb7tBTWPd5Y8BDFdMm9BJR_MNI4isf8p8n4D5pHA");
        putMessageBody.put("transferState", "COMMITTED");
        putMessageBody.put("completedTimestamp", "2018-09-13T08:38:08.699-04:00");

        try {
            Thread.sleep(5);
            URL obj = new URL( Switch_URL );

            HttpClient httpClient = HttpClientBuilder.create().build();
            logger.info("URL: " + Switch_URL);
            HttpPut putter=new HttpPut( Switch_URL );
            putter.setHeader("FSPIOP-Source","dfsp2");
            putter.setHeader("FSPIOP-Destination","dfsp1");
            putter.setHeader("Date","2018-08-17");
            putter.setHeader("Accept","1.0");
            putter.setHeader("Content-type", "application/json");

            StringEntity se = new StringEntity( putMessageBody.toString());
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
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

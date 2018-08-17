package analytics;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;


public class AnalyticsGenerator {

    public static void main(String[] args) throws ParseException, IOException {

        BufferedReader brCallbacks = new BufferedReader(new FileReader("CallbacksReceived.csv"));
        String line =  null;
        HashMap<String,Long> callbackTimes = new HashMap();
        while((line=brCallbacks.readLine())!=null){
            String str[] = line.split(",");
            callbackTimes.putIfAbsent(str[0],Long.valueOf( str[1] ) );
        }
        //System.out.println(callbackTimes);

        BufferedReader brJMeter = new BufferedReader(new FileReader("TransferIDList-JMeter.csv"));
        HashMap<String,Long> jmeterTimes = new HashMap();
        while((line=brJMeter.readLine())!=null){
            String str[] = line.split(",");
            jmeterTimes.putIfAbsent(str[0],Long.valueOf( str[1] ) );
        }
        //System.out.println(jmeterTimes);

        System.out.println("\n********* METRICS in ms*************\n");
        System.out.println("UUID || JMeterTime || CallbackReceivedTime || ResponseTime");

        int totalResponseTimeSoFar = 0;
        int minTime=9999, maxTime=0;

        for (Map.Entry<String, Long> entry:jmeterTimes.entrySet()
             ) {
            int responseTime = (int) abs( entry.getValue() - callbackTimes.get( entry.getKey() ) );
            totalResponseTimeSoFar = totalResponseTimeSoFar + responseTime;
            if (minTime > responseTime)
                minTime = responseTime;
            if (maxTime < responseTime)
                maxTime = responseTime;
            System.out.println( entry.getKey() + entry.getValue() + ",\t" + callbackTimes.get( entry.getKey() ) + " \t::\t" + responseTime);
        }

        System.out.println("\nAverage Response Time: " + ( totalResponseTimeSoFar/jmeterTimes.size() ) );

        System.out.println("\nTime elapsed (from first request in JMeter to last callback received): " + abs( Collections.min( jmeterTimes.values() ) - Collections.max( callbackTimes.values() ) ) );
        System.out.println("Number of successful transfers): " + jmeterTimes.size() );
        System.out.println("\nMin Time: " + minTime );
        System.out.println("Max Time: " + maxTime );
        System.out.println("TPS: " + ( jmeterTimes.size() / ( abs( Collections.min( jmeterTimes.values() ) - Collections.max( callbackTimes.values() ) )/1000 ) ) );

        brCallbacks.close(); brJMeter.close();
    }

}

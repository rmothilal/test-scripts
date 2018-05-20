package com.mojaloop;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Queue;

@SpringBootApplication
@EnableJms
public class MainApplication {

    @Bean
    public Queue partiesQueue() {
        return new ActiveMQQueue("parties.queue");
    }

    @Bean
    public Queue quotesQueue() {
        return new ActiveMQQueue("quotes.queue");
    }

    @Bean
    public Queue transfersQueue() {
        return new ActiveMQQueue("transfers.queue");
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}

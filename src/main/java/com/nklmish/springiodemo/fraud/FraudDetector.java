package com.nklmish.springiodemo.fraud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class FraudDetector {

    public static void main(String[] args) {
        SpringApplication.run(FraudDetector.class, args);
    }

    @Component
    public static class Listener {

        @KafkaListener(id = "c14", topics = "txn-jdbc-kafka")
        @SendTo("frauds")
        public String filter(@Payload String payload) {
            System.out.println("received = " + payload);
            if (payload.contains("bar")) {
                return payload;
            }
            return null;
        }
    }
}

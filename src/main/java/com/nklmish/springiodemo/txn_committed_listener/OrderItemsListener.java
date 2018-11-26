package com.nklmish.springiodemo.txn_committed_listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class OrderItemsListener {

    public static void main(String[] args) {
        SpringApplication.run(OrderItemsListener.class, args);
    }

    @Component
    public static class Listener {

        @KafkaListener(id = "someid-xx", topics = "txn-jdbc-kafka")
        public void orders(@Payload String payload) {
            System.out.println("received = " + payload);
        }
    }
}

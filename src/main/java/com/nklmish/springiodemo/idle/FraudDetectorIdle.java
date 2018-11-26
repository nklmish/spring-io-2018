package com.nklmish.springiodemo.idle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.event.ListenerContainerIdleEvent;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class FraudDetectorIdle {

    public static void main(String[] args) {
        SpringApplication.run(FraudDetectorIdle.class, args);
    }

    @Component
    public static class Listener {

        @KafkaListener(id = "c199", topics = "txn-jdbc-kafka")
        @SendTo("frauds")
        public String filter(@Payload String payload) {
            System.out.println("received = " + payload);
            if (payload.contains("bar")) {
                return payload;
            }
            return null;
        }
    }

    @EventListener(condition = "event.listenerId.startsWith('c15-')")
    public void eventHandler(ListenerContainerIdleEvent event) {
        System.out.println("event = " + event);


    }
}

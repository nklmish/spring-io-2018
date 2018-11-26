package com.nklmish.springiodemo.demo2;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@SpringBootApplication
public class Demo2 {

    public static void main(String[] args) {
        SpringApplication.run(Demo2.class, args);
    }

    @Component
    public static class FooListener {

//        @KafkaListener(groupId = "foos", topics = "foos")
//        public void foo(String foo) {
//            System.out.println("received = " + foo);
//        }

//        @KafkaListener(groupId = "foos", topics = "foos")
//        public void foo(ConsumerRecord cr) {
//            System.out.println("value:" + cr.value() + ",partition:" + cr.partition() + ", offset:" + cr.offset());
//        }



//        @KafkaListener(groupId = "foos",
//                topicPartitions = {@TopicPartition(topic = "foos", partitions = {"0"})})
//        public void foo(String foo) {
//            System.out.println("received = " + foo);
//        }

//
//        @KafkaListener(groupId = "foos",
//                topics = "foos",
//                containerFactory = "cf")
//        public void foo(String foo) {
//            System.out.println("received = " + foo);
//        }


//
//        @KafkaListener(groupId = "foos",
//                topics = "foos",
//                containerFactory = "cf",
//                errorHandler = "eh")
//        public void foo(String foo) {
//            throw new RuntimeException("should invoke error handler");
//        }


//        @KafkaListener(groupId = "foos", topics = "foos")
//        public void foo(String foo, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
//            System.out.println("received = " + foo + ", from partition: " + partition);
//        }



                @KafkaListener(groupId = "foos", topics = "foos")
        public void foo(@Payload (required = false) String foo) { //@Payload (required = false) , for compacted topic (used to "delete" keys.)
            System.out.println("received = " + foo);
        }

        @Bean
        public ConcurrentKafkaListenerContainerFactory cf(ConsumerFactory consumerFactory,
                                                          KafkaTemplate kafkaTemplate) {
            ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
            factory.setReplyTemplate(kafkaTemplate);
            factory.setConsumerFactory(consumerFactory);
            factory.setStatefulRetry(true);
            return factory;
        }


        @Bean
        public KafkaListenerErrorHandler eh() {
            return (ConsumerAwareListenerErrorHandler) (message, exception, consumer) -> {
                System.out.println("assignments = " + consumer.assignment());
                System.out.println(message);
                return null;
            };
        }
    }
}

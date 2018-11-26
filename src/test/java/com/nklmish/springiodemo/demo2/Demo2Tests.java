package com.nklmish.springiodemo.demo2;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.messaging.support.MessageBuilder.withPayload;

@RunWith(SpringRunner.class)
@SpringBootTest
@EmbeddedKafka(topics = "foos")
public class Demo2Tests {

    @Autowired
    private KafkaTemplate<Object, Object> template;

    @Test
    public void testFoos() throws InterruptedException {
//        template.send(withPayload("foo")
//                              .setHeader(KafkaHeaders.TOPIC, "foos")
//                              .setHeader(KafkaHeaders.PARTITION_ID, 0)
//                              .build());

        template.send("foos", 0, null, null);
        template.flush();

        Thread.sleep(5_000);
    }
}

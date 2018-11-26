package com.nklmish.springiodemo.demo3;

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
@EmbeddedKafka(topics = "fruits")
// partition0 -> []
// partition1 -> [Apple, Banana]
//                  0    1
public class Demo3Tests {

    @Autowired
    private KafkaTemplate<Object, Object> template;

    @Test
    public void testFruits() throws InterruptedException {
        template.send(withPayload(new Demo3.Apple("green"))
                              .setHeader(KafkaHeaders.TOPIC, "fruits")
                              .setHeader(KafkaHeaders.PARTITION_ID, 1)
                              .build());

        template.send(withPayload(new Demo3.Banana("banana"))
                              .setHeader(KafkaHeaders.TOPIC, "fruits")
                              .setHeader(KafkaHeaders.PARTITION_ID, 1)
                              .build());

        Thread.sleep(5_000);
    }
}


//new String(producerRecord.headers().toArray()[0].value())
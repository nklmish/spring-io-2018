package com.nklmish.springiodemo.demo1;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author nklmish
 * Twitter: @nklmish
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EmbeddedKafka(topics = "demo1")
public class Demo1_SendMessageToKafka_EmbeddedKafkaAnnotation {

    private static final String SOME_TOPIC = "demo1";

    @Autowired
    private KafkaTemplate<Object, Object> template;

    @Autowired
    private KafkaEmbedded kafkaEmbedded;

    @Test
    public void testPublishMessagesToKafka() {
        template.send(SOME_TOPIC, "hello");
    }
}
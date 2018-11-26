package com.nklmish.springiodemo.demo1;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author nklmish
 * Twitter: @nklmish
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EmbeddedKafka(topics = "demo13")
public class Demo13_KafkaTemplateCallback {

    private static final String SOME_TOPIC = "demo13";

    @Autowired
    private KafkaTemplate<Object, Object> template;


    @Test
    public void testPublishMessagesToKafka() throws InterruptedException {
        ListenableFuture<SendResult<Object, Object>> future = template.send(SOME_TOPIC, "hello");
        future.addCallback(new ListenableFutureCallback<SendResult<Object, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("ex = " + ex);
            }

            @Override
            public void onSuccess(SendResult<Object, Object> result) {
                System.out.println("result.getProducerRecord() = " + result.getProducerRecord().topic());

            }
        });

        Thread.sleep(2000);
    }
}
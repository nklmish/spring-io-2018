package com.nklmish.springiodemo.demo1;

import com.nklmish.springiodemo.KafkaEmbeddedHolder;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author nklmish
 * Twitter: @nklmish
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo1_SendMessageToKafka_KafkaEmbeddedBean {

    private static final String SOME_TOPIC = "demo1";

    private static final KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, false, SOME_TOPIC);

    @BeforeClass
    public static void setUp() throws Exception {
        embeddedKafka.before();
    }

    @Autowired
    private KafkaTemplate<Object, Object> template;

    @Test
    public void testPublishMessagesToKafka() {
        template.send(SOME_TOPIC, "hello");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        embeddedKafka.destroy();
    }
}
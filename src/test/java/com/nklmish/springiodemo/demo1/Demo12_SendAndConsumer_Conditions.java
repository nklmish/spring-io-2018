package com.nklmish.springiodemo.demo1;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.kafka.test.assertj.KafkaConditions.key;
import static org.springframework.kafka.test.assertj.KafkaConditions.partition;
import static org.springframework.kafka.test.assertj.KafkaConditions.timestamp;
import static org.springframework.kafka.test.assertj.KafkaConditions.value;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo12_SendAndConsumer_Conditions {

    private static final String SOME_TOPIC = "demo12";

    private static Consumer<String, String> consumer;

    @ClassRule
    public static final KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, false, SOME_TOPIC);

    @Autowired
    private KafkaTemplate<Object, Object> template;

    @BeforeClass
    public static void setUp() throws Exception {
        ConsumerFactory<String, String> cf = new DefaultKafkaConsumerFactory<>(generateConfigs());
        consumer = cf.createConsumer();

        embeddedKafka.consumeFromAnEmbeddedTopic(consumer, SOME_TOPIC);
    }

    private static Map<String, Object> generateConfigs() {
        return KafkaTestUtils.consumerProps("whatever", "false", embeddedKafka);
    }

    @Test
    public void testPublishMessagesToKafka() {
        template.send(SOME_TOPIC, 0, 1526145422L, null,"hello");
        template.flush();
        ConsumerRecord<String, String> received = KafkaTestUtils.getSingleRecord(consumer, SOME_TOPIC);

        assertThat(received).has(key(null));
        assertThat(received).has(value("hello"));
        assertThat(received).has(partition(0));
        assertThat(received).has(timestamp(1526145422L));
    }

    @AfterClass
    public static void tearDown() {
        consumer.close();
    }
}
package com.nklmish.springiodemo.demo1;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.kafka.test.assertj.KafkaConditions.key;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo13_CustomKafkaTemplate {

    private static final String SOME_TOPIC = "demo13";

    private static Consumer<Integer, String> consumer;

    @ClassRule
    public static final KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, false, SOME_TOPIC);

    @BeforeClass
    public static void setUp() throws Exception {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("whatever", "false", embeddedKafka);
        DefaultKafkaConsumerFactory<Integer, String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);
        consumer = cf.createConsumer();
        embeddedKafka.consumeFromAnEmbeddedTopic(consumer, SOME_TOPIC);
    }

    @Test
    public void testPublishMessagesToKafka() throws Exception {
        DefaultKafkaProducerFactory<Integer, String> pf = createProducer();
        KafkaTemplate<Integer, String> template = new KafkaTemplate<>(pf, true);

        template.send(SOME_TOPIC, 0, 42, "foo");
        ConsumerRecord<Integer, String> received = KafkaTestUtils.getSingleRecord(consumer, SOME_TOPIC);

        assertThat(received).has(key(42));

        pf.destroy();
    }

    private DefaultKafkaProducerFactory<Integer, String> createProducer() {
        Map<String, Object> configs = KafkaTestUtils.producerProps(embeddedKafka);
        return new DefaultKafkaProducerFactory<>(configs);
    }


    @AfterClass
    public static void tearDown() {
        consumer.close();
    }
}
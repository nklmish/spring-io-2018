package com.nklmish.springiodemo.demo1;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.record.TimestampType;
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
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasKey;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasPartition;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasTimestamp;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo12_SendAndConsumer_Matchers {

    private static final String SOME_TOPIC = "demo12";

    private static Consumer<String, String> consumer;

    @Autowired
    private KafkaTemplate<Object, Object> template;

    @ClassRule
    public static final KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, false, SOME_TOPIC);

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

        Assert.assertThat(received, hasKey(null));
        Assert.assertThat(received, hasValue("hello"));
        Assert.assertThat(received, hasPartition(0));
        Assert.assertThat(received, hasTimestamp(TimestampType.CREATE_TIME, 1526145422L));
    }

    @AfterClass
    public static void tearDown() {
        consumer.close();
    }
}
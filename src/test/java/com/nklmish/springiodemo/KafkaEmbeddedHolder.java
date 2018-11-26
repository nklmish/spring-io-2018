package com.nklmish.springiodemo;

import kafka.common.KafkaException;
import org.springframework.kafka.test.rule.KafkaEmbedded;

public final class KafkaEmbeddedHolder {

	private static KafkaEmbedded kafkaEmbedded = new KafkaEmbedded(1, false);

	private static boolean started;

	public static KafkaEmbedded getKafkaEmbedded() {
		if (!started) {
			try {
				kafkaEmbedded.before();
			}
			catch (Exception e) {
				throw new KafkaException(e);
			}
			started = true;
		}
		return kafkaEmbedded;
	}

	private KafkaEmbeddedHolder() {
		super();
	}

}
// In your test class access it like this
//static {
//    KafkaEmbeddedHolder.getKafkaEmbedded().addTopics(topic1, topic2);
//}
//
//private static KafkaEmbedded embeddedKafka = KafkaEmbeddedHolder.getKafkaEmbedded();
package com.nklmish.springiodemo.demoConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public KafkaProperties properties() {
		KafkaProperties properties = new KafkaProperties();
		properties.setBootstrapServers(Arrays.asList("localhost:9092"));
		//....
		return new KafkaProperties();
	}

	//Or you can inject KafkaProperties
	public void appendNewProperties(KafkaProperties properties) {
//		properties.getProducer().setXXX();
	}
}

package com.nklmish.springiodemo.txn;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    @Bean
    public ChainedTransactionManager chainedTransactionManager(KafkaTransactionManager<?, ?> kafka, JpaTransactionManager jpa) {
        return new ChainedTransactionManager(kafka, jpa);
    }

    @Bean
    public ApplicationRunner runner(OrderService orderService) {
        return x -> orderService.book();
    }
}
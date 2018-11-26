package com.nklmish.springiodemo.demo3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.converter.Jackson2JavaTypeMapper;
import org.springframework.kafka.support.converter.MessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Demo3 {

    public static void main(String[] args) {
        SpringApplication.run(Demo3.class, args);
    }

    @Component
    @KafkaListener(id = "fruit-consumer-group", topics = "fruits")
    public static class FruitListener {

        @KafkaHandler
        public void apple(Apple apple) {
            System.out.println("received = " + apple);
        }

        @KafkaHandler
        public void banana(Banana banana) {
            System.out.println("received = " + banana);
        }
    }



    @Bean
    public MessageConverter mc() {
        StringJsonMessageConverter converter = new StringJsonMessageConverter();
        DefaultJackson2JavaTypeMapper mapper = new DefaultJackson2JavaTypeMapper();
        mapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
        mapper.addTrustedPackages("com.nklmish.springiodemo.demo3");
        converter.setTypeMapper(mapper);
        return converter;
    }

    public static class Apple {

        private String color;

        public Apple() {
        }

        public Apple(String color) {
            this.color = color;
        }


        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        @Override
        public String toString() {
            return "Apple{" +
                    "color='" + color + '\'' +
                    '}';
        }
    }

    public static class Banana {

        private String color;

        public Banana() {
        }

        public Banana(String color) {
            this.color = color;
        }

        public String getColor() {
            return color;
        }

        @Override
        public String toString() {
            return "Banana{" +
                    "color='" + color + '\'' +
                    '}';
        }
    }
}

package com.example.gh608;

import java.util.function.Consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class Gh608Application {

    public static void main(String[] args) {
        SpringApplication.run(Gh608Application.class, args);
    }

    //	@Bean
    //	Consumer<Message<Foo>> input() {
    //		return message -> System.out.println("Consumer<Message<Foo>> : " + message.getPayload());
    //	}

    @Bean
    Consumer<Foo> input() {
        return payload -> System.out.println("Consumer<Foo> : " + payload);
    }

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    public static class Foo {

        private Bar bar;

        public Bar getBar() {
            return this.bar;
        }

        public void setBar(Bar bar) {
            this.bar = bar;
        }

        @Override
        public String toString() {
            return "Foo [bar=" + this.bar + "]";
        }

    }

    public static class Bar {
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "Bar [message=" + this.message + "]";
        }

    }

}

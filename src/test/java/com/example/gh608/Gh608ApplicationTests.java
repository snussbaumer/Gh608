package com.example.gh608;

import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.example.gh608.Gh608ApplicationTests.TestApplication;
import com.example.gh608.Gh608ApplicationTests.TestApplication.Foo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = TestApplication.class)
@Import(TestChannelBinderConfiguration.class)
class Gh608ApplicationTests {

	@Autowired
	private InputDestination inputDestination;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void test() throws Exception {
		Foo foo = new Foo();
		foo.setMessage("hello world");
		byte[] payload = objectMapper.writeValueAsBytes(foo);
		Message<byte[]> message = MessageBuilder.withPayload(payload).setHeader("contentType", "application/json").build();
		inputDestination.send(message);
		Thread.sleep(1000);
	}

	@SpringBootApplication
	public static class TestApplication {

		public static void main(String[] args) {
			SpringApplication.run(TestApplication.class, args);
		}

		@Bean
		Consumer<Message<Foo>> input() {
			return message -> System.out.println("Consumer<Message<Foo>> : " + message.getPayload());
		}

//		@Bean
//		Consumer<Foo> input() {
//			return payload -> System.out.println("Consumer<Foo> : " + payload);
//		}

		@Bean
		ObjectMapper objectMapper() {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return objectMapper;
		}

		public static class Foo {
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

}

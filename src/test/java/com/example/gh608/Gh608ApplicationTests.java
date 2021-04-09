package com.example.gh608;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.example.gh608.Gh608Application.Bar;
import com.example.gh608.Gh608Application.Foo;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class Gh608ApplicationTests {

	@Autowired
	private InputDestination inputDestination;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void test() throws Exception {
		Bar bar = new Bar();
		bar.setMessage("hello world");
		Foo foo = new Foo();
		foo.setBar(bar);
		byte[] payload = objectMapper.writeValueAsBytes(foo);
		Message<byte[]> message = MessageBuilder.withPayload(payload).setHeader("contentType", "application/json").build();
		inputDestination.send(message);
		Thread.sleep(1000);
	}

}

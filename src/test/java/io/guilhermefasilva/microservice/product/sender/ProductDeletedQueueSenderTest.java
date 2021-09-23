package io.guilhermefasilva.microservice.product.sender;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.guilhermefasilva.microservice.product.feature.ScenarioFactory;

@RunWith(MockitoJUnitRunner.class)
public class ProductDeletedQueueSenderTest {
	
	@InjectMocks
	private ProductDeletedQueueSender productDeleted;
	
	@Mock
	private RabbitTemplate rabbitTemplate;
	
	
	
	@Test
	public void sendMessage_SendMessageQueue_ExpectedSuccess() throws JsonProcessingException{
		var productMessage = ScenarioFactory.newProduct();
		var queue = "teste";
		
		ReflectionTestUtils.setField(productDeleted, "nameQueue", queue);
		
		productDeleted.sendMessage(productMessage);
		
		verify(rabbitTemplate,times(1)).convertAndSend(eq(queue), any(Message.class));
	}

}

package io.guilhermefasilva.microservice.product.sender;


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@RunWith(MockitoJUnitRunner.class)
public class ProductDeletedQueueSenderTest {
	
	@InjectMocks
	private ProductDeletedQueueSender productDeleted;
	
	@Mock
	private RabbitTemplate rabbitTemplate;
	
	@Test
	public void sendMessage_SendMessageQueue_ExpectedSuccess(){
		var message = Object.class;
	
			productDeleted.sendMessage(message);
		
		
	}

}

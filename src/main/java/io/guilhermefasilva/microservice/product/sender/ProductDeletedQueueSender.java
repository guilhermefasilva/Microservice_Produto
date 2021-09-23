package io.guilhermefasilva.microservice.product.sender;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.guilhermefasilva.microservice.product.domain.models.Product;


@Service
public class ProductDeletedQueueSender {
	
		@Autowired
		private RabbitTemplate rabbitTemplate;
		
		@Value("${product.deleted.queue}")
		private String nameQueue;
		
		public void sendMessage(Product productMessage) throws JsonProcessingException {	
				
						String messageJson = new ObjectMapper().writeValueAsString(productMessage);
						Message messageSender = MessageBuilder
								.withBody(messageJson.getBytes())
								.setContentType(MessageProperties.CONTENT_TYPE_JSON)
								.build();
						this.rabbitTemplate.convertAndSend(this.nameQueue, messageSender);
		}
		
}
 
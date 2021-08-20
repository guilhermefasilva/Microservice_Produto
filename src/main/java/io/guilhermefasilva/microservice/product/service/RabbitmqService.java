package io.guilhermefasilva.microservice.product.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class RabbitMqService {
	
		@Autowired
		private RabbitTemplate rabbitTemplate;
		
		@Value("${spring.rabbitmq.queue}")
		private String nameQueue;
		
		public void sendMessage(Object message) {	
				try {
					String json = new ObjectMapper().writeValueAsString(message);
					this.rabbitTemplate.convertAndSend(this.nameQueue, json );
				}catch (JsonProcessingException e) {
						e.printStackTrace();
				}	
		}
		
}

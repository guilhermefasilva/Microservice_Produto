package io.guilhermefasilva.microservice.product.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service

public class RabbitmqService {
	
		@Autowired
		private RabbitTemplate rabbitTemplate;
		
		public void enviaMensagem(String nomeFila, Object mensagem) {
			
			try {
				String json = new ObjectMapper().writeValueAsString(mensagem);
				this.rabbitTemplate.convertAndSend(nomeFila, json );
			}catch (JsonProcessingException e) {
					e.printStackTrace();
			}
			
		}
		
}

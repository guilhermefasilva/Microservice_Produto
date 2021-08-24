package io.guilhermefasilva.microservice.product.configuration;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {
	
	
	@Value("${product.deleted.queue}")
	private String nomeQueue;
			
	@Value("${product.deleted.exchange}")
	private String nameExchange;
	
	@Autowired
	private AmqpAdmin amqpAdmin;
	
	
	private Queue fila(String nomeFila) {
		return new Queue(nomeFila, true, false, false);
	}
	
	private DirectExchange trocaDireta() {
		return new DirectExchange(nameExchange);
	}
	
	private Binding relacionamento (Queue fila, DirectExchange troca) {
		return new Binding(fila.getName(), Binding.DestinationType.QUEUE, troca.getName(), fila.getName(), null);
	}
	
	@PostConstruct
	private void adiciona() {
		
		Queue filaProduto = this.fila(nomeQueue);
		
		DirectExchange troca = this.trocaDireta();
		
		Binding ligacaoProduto = this.relacionamento(filaProduto, troca);
		
		this.amqpAdmin.declareQueue(filaProduto);
		
		this.amqpAdmin.declareExchange(troca);
		
		this.amqpAdmin.declareBinding(ligacaoProduto);
		
	}
}

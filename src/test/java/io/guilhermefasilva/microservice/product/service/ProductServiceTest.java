package io.guilhermefasilva.microservice.product.service;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoRequest;
import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoRequestUpdate;
import io.guilhermefasilva.microservice.product.domain.models.Product;
import io.guilhermefasilva.microservice.product.exception.ResourceNotFoundException;
import io.guilhermefasilva.microservice.product.repository.ProductRepository;
import io.guilhermefasilva.microservice.product.sender.ProductDeletedQueueSender;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
	
	@InjectMocks
	private ProductService productService;

	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private ProductDtoRequest productRequest;
	
	@Mock
	private Product produto;
	
	@Mock
	private ProductDtoRequestUpdate productUpdate;
	
	@Mock
	private ProductDeletedQueueSender rabbitDeleteQueue;
	
	@Mock
	private Page<Product> productPage;
	
	
	
	
	
	
	@Test
	public void deveSalvarUmproduto() {
			//cenario
		    when(this.productRepository.save((Product) any())).thenReturn(produto);
		   
		    //ação
		    this.productService.save(productRequest);
		    
		    //verificação
		    verify(this.productRepository, times(1)).save((Product) any());
		

	}
	
	@Test
	public void deveRetornarumProdutoPorId() {
		
		//Cenario
		Optional<Product> product = Optional.<Product>of(produto);
		when(productRepository.findById((Long) any())).thenReturn(product);
		
		//Ação
		this.productService.findById(any());
		
		//verificação
		verify(this.productRepository, times(1)).findById((Long)any());
	}
	
	@Test
	public void deveAtualizarUmProdutoPassandoId() {
		//cenario
		Optional<Product> productOptional = Optional.<Product>of(produto);
		when(productRepository.findById((Long) any())).thenReturn(productOptional).thenThrow(ResourceNotFoundException.class);
		when(productRepository.save((Product) any())).thenReturn(produto);
		
		//ação
		this.productService.update(any(), productUpdate);
		
		//verificação
		verify(this.productRepository, times(1)).save((Product) any());
		
	}
	
	@Test
	public void deveRetornarUmaListaDeProdutos() {
		//cenario
		when(this.productRepository.findByNome(any(), any())).thenReturn(productPage);
		
		//ação
		this.productService.findAll(any(),any());
	
		//verificação
		verify(this.productRepository, times(1)).findByNome(any(), any());
		
	}
	
	@Test
	public void deveDeletarUmProduto() {
		//cenario
		Optional<Product> productOptional = Optional.<Product>of(produto);
		when(productRepository.findById((Long)any())).thenReturn(productOptional).thenThrow(ResourceNotFoundException.class);
		doNothing().when(productRepository).delete((Product)any());
		
		//ação
		this.productService.delete(any());
		this.rabbitDeleteQueue.sendMessage(productOptional);
		
		//verify
		verify(this.productRepository, times(1)).delete((Product)any());
		verify(this.rabbitDeleteQueue, times(1)).sendMessage(productOptional);
		
		
	}

	
	

}

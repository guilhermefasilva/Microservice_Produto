package io.guilhermefasilva.microservice.product.service;


import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.beans.factory.annotation.Autowired;

import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoRequest;
import io.guilhermefasilva.microservice.product.domain.models.Product;
import io.guilhermefasilva.microservice.product.repository.ProductRepository;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
	
	@InjectMocks
	private ProductService productService;

	@Mock
	private ProductRepository productRepository;
	
	@Autowired
	@Mock
	private ModelMapper modelMapper;
	
	@Autowired
	@Mock
	private ProductDtoRequest productRequest;
	
	
	
	
	@Test
	public void deveSalvarUmproduto() {
			//cenario
		    Product produtoMapper = modelMapper.map(productRequest, Product.class);
		    when(this.productRepository.save((Product) any())).thenReturn(produtoMapper);
		   
		    //ação
		    this.productService.save(productRequest);
		    
		    //verificação
		    verify(this.productRepository, times(1)).save((Product) any());
		

	}
	
	@Test
	public void deveRetornarumProdutoPorId() {
		
		//Cenario
		Product produto = new Product();
		Optional<Product> ofResult = Optional.<Product>of(produto);
		when(productRepository.findById((Long) any())).thenReturn(ofResult);
		
		//Ação
		this.productService.findById(1L);
		
		//verificação
		verify(this.productRepository, times(1)).findById((Long)any());
	}
	

	
	

}

package io.guilhermefasilva.microservice.product.service;




import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
	public void validate_SaveProduct_ExpectedSucess() {
			//cenario
		    when(this.productRepository.save(any())).thenReturn(produto);
		    //ação
		    this.productService.save(productRequest);
		    //verificação
		    verify(this.productRepository, times(1)).save(any());
	}
	
	@Test
	public void validate_ProductFind_ExpectedSucess() {
		
		//Cenario
		Optional<Product> product = Optional.of(produto);
		when(productRepository.findById(any())).thenReturn(product);
		//Ação
		this.productService.findById(any());
		//verificação
		verify(this.productRepository, times(1)).findById(any());
	}
	
	@Test
	public void validate_ProductNotFind_ExpectedThrownException() {
		//cenario
		when(productRepository.findById(any())).thenThrow(ResourceNotFoundException.class);
		//ação
		assertThatThrownBy(()->productService.findById(any()))
							.isInstanceOf(RuntimeException.class)
							.withFailMessage("Resource not found. Id: ",produto.getId());
		//verificação
			verify(this.productRepository, times(1)).findById(any());
							
	}
	
	@Test
	public void validate_UpdateProductFindById_ExpectedSucess() {
		//contemplar 2 cenrarios
		//cenario
		Optional<Product> productOptional = Optional.of(produto);
		when(productRepository.findById(any(Long.class))).thenReturn(productOptional);
		when(productRepository.save(any(Product.class))).thenReturn(produto);
		//ação
		this.productService.update(any(Long.class), productUpdate);
		//verificação
		verify(this.productRepository, times(1)).save(any(Product.class));
		
	}
	
	@Test
	public void validate_ProductUpdateNotFindById_ExpectedThrownException() {
		//cenario
		when(productRepository.findById(any(Long.class))).thenThrow(ResourceNotFoundException.class);
		//ação
		assertThatThrownBy(()->productService.update(any(Long.class), productUpdate))
							.isInstanceOf(RuntimeException.class)
							.withFailMessage("Resource not found. Id:",produto.getId());
		//verificação
		verify(this.productRepository, times(1)).findById(any(Long.class));
		
	}
	
	@Test
	public void validate_ProductAllList_ExpectedSucess() {
		//cenario
		when(this.productRepository.findByNome(any(), any())).thenReturn(productPage);
		//ação
		this.productService.findAll(any(),any());
		//verificação
		verify(this.productRepository, times(1)).findByNome(any(), any());
		
	}
	
	@Test
	public void validate_ProductDeleteFindById_ExpectedSucess() {
		//cenario
		Optional<Product> productOptional = Optional.of(produto);
		when(productRepository.findById(any())).thenReturn(productOptional);
		doNothing().when(productRepository).delete(any(Product.class));
		//ação
		this.productService.delete(any());
		this.rabbitDeleteQueue.sendMessage(productOptional);
		//verify
		verify(this.productRepository, times(1)).delete(any(Product.class));
		verify(this.rabbitDeleteQueue, times(1)).sendMessage(productOptional);
	}
	
	@Test
	public void validate_ProductNotFindById_ExpectedThrownException() {
		//cenario
		when(productRepository.findById(any(Long.class))).thenThrow(ResourceNotFoundException.class);
		//ação
		assertThatThrownBy(()->productService.delete(any(Long.class)))
							.isInstanceOf(RuntimeException.class)
							.withFailMessage("Resource not found. Id:",produto.getId());
		//verificação
		verify(this.productRepository, times(1)).findById(any(Long.class));
	}
}

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
		    when(this.productRepository.save(any())).thenReturn(produto);
		   
		    this.productService.save(productRequest);
		   
		    verify(this.productRepository, times(1)).save(any());
	}
	
	@Test
	public void validate_ProductFind_ExpectedSucess() {
		
		Optional<Product> product = Optional.of(produto);
		when(productRepository.findById(any())).thenReturn(product);
		
		this.productService.findById(any());
		
		verify(this.productRepository, times(1)).findById(any());
	}
	
	
	
	@Test
	public void validate_UpdateProductFindById_ExpectedSucess() {

		Optional<Product> productOptional = Optional.of(produto);
		when(productRepository.findById(any(Long.class))).thenReturn(productOptional);
		when(productRepository.save(any(Product.class))).thenReturn(produto);

		this.productService.update(any(Long.class), productUpdate);

		verify(this.productRepository, times(1)).save(any(Product.class));
		
	}
	
	
	
	@Test
	public void validate_ProductAllList_ExpectedSucess() {

		when(this.productRepository.findByNome(any(), any())).thenReturn(productPage);

		this.productService.findAll(any(),any());

		verify(this.productRepository, times(1)).findByNome(any(), any());
		
	}
	
	@Test
	public void validate_ProductDeleteFindById_ExpectedSucess() {

		Optional<Product> productOptional = Optional.of(produto);
		when(productRepository.findById(any())).thenReturn(productOptional);
		doNothing().when(productRepository).delete(any(Product.class));

		this.productService.delete(any());
		this.rabbitDeleteQueue.sendMessage(productOptional);

		verify(this.productRepository, times(1)).delete(any(Product.class));
		verify(this.rabbitDeleteQueue, times(1)).sendMessage(productOptional);
	}
	
	@Test
	public void validate_ProductDeleteNotFindById_ExpectedThrownException() {
 
		when(productRepository.findById(any(Long.class))).thenThrow(ResourceNotFoundException.class);
 
		assertThatThrownBy(()->productService.delete(any(Long.class)))
							.isInstanceOf(RuntimeException.class)
							.withFailMessage("Resource not found. Id:",produto.getId());

		verify(this.productRepository, times(1)).findById(any(Long.class));
	}
	
	
	@Test
	public void validate_ProductNotFindById_ExpectedThrownException() {

		when(productRepository.findById(any())).thenThrow(ResourceNotFoundException.class);

		assertThatThrownBy(()->productService.findById(any()))
							.isInstanceOf(RuntimeException.class)
							.withFailMessage("Resource not found. Id: ",produto.getId());

		verify(this.productRepository, times(1)).findById(any());
							
	}
	
	@Test
	public void validate_ProductUpdateNotFindById_ExpectedThrownException() {
		
		when(productRepository.findById(any(Long.class))).thenThrow(ResourceNotFoundException.class);

		assertThatThrownBy(()->productService.update(any(Long.class), productUpdate))
							.isInstanceOf(RuntimeException.class)
							.withFailMessage("Resource not found. Id:",produto.getId());

		verify(this.productRepository, times(1)).findById(any(Long.class));
		
	}
	
	
	
}

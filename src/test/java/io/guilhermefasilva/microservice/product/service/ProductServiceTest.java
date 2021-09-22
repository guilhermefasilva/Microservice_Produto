package io.guilhermefasilva.microservice.product.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoResponse;
import io.guilhermefasilva.microservice.product.domain.models.Product;
import io.guilhermefasilva.microservice.product.exception.ResourceNotFoundException;
import io.guilhermefasilva.microservice.product.feature.ScenarioFactory;
import io.guilhermefasilva.microservice.product.repository.ProductRepository;
import io.guilhermefasilva.microservice.product.sender.ProductDeletedQueueSender;


@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

	@InjectMocks
	private ProductService productService;

	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private ProductDeletedQueueSender rabbitDeleteQueue;
	
	
	
	@Test
	public void save_WhenReceiveValidProduct_ExpectedSucess() { 
		var product = ScenarioFactory.newProduct();
		var productRequest = ScenarioFactory.newProductRequest();  
		var productResponse = ScenarioFactory.newProductDtoResponse();
		
			when(modelMapper.map(productRequest, Product.class)).thenReturn(product);
		    when(productRepository.save(product)).thenReturn(product);
		    when(modelMapper.map(product, ProductDtoResponse.class)).thenReturn(productResponse);
		    
		    var saveProductRequest = productService.save(productRequest);
		 
		    assertNotNull(saveProductRequest);
		    verify(productRepository, times(1)).save(product);
		    verify(modelMapper, times(1)).map(product, ProductDtoResponse.class);
	}
	
	
	@Test
	public void findById_WhenReceiveValidIdProduct_ExpectedSucess() {
		var product = ScenarioFactory.newProduct();
		var productOptional = ScenarioFactory.newOptionalProduct();
		var productResponse = ScenarioFactory.newProductDtoResponse();
		
		when(productRepository.findById(eq(product.getId()))).thenReturn(productOptional);
		when(modelMapper.map(product, ProductDtoResponse.class)).thenReturn(productResponse);
		
		var findProduct = productService.findById(1L);
		
		assertEquals(productResponse, findProduct);
		verify(productRepository, times(1)).findById(1L);
		verify(modelMapper, times(1)).map(product, ProductDtoResponse.class);
	}
	
	@Test
	public void findById_WhenReceiveInvalidIdProduct_ExpectedThrownException() {
			var product = ScenarioFactory.newProduct();
			
			when(productRepository.findById(2L)).thenReturn(Optional.empty());

		assertThatThrownBy(()->productService.findById(2L))
							.isInstanceOf(ResourceNotFoundException.class)
							.hasMessageContaining("Resource not found. Id:", product.getId());

		verify(productRepository, times(1)).findById(2L);
							
	}
	
	
	
	@Test
	public void update_WhenReceiveValidProductIdUpdateProduct_ExpectedSucess() {
			var product = ScenarioFactory.newProduct();
			var productUpdate = ScenarioFactory.newProductRequestDtoUpdate();
			var productResponse = ScenarioFactory.newProductDtoResponse();
			var productOptional = ScenarioFactory.newOptionalProduct();	
			
		when(productRepository.findById(eq(product.getId()))).thenReturn(productOptional);
		product.setDescricao(productUpdate.getDescricao());
		when(productRepository.save(product)).thenReturn(product);
		when(modelMapper.map(product, ProductDtoResponse.class)).thenReturn(productResponse);
		
		var productSaveUpdate = productService.update(1L, productUpdate);
		
		assertNotNull(productSaveUpdate);
		verify(productRepository, times(1)).save(product);
		verify(modelMapper, times(1)).map(product, ProductDtoResponse.class);
		
	}
	
	@Test
	public void update_ProductUpdateNotFindById_ExpectedThrownException() {
		var product = ScenarioFactory.newProduct();	
		var productUpdate = ScenarioFactory.newProductRequestDtoUpdate();
		
		when(productRepository.findById(2L)).thenReturn(Optional.empty());
		
		assertThatThrownBy(()->productService.update(2L, productUpdate))
							.isInstanceOf(ResourceNotFoundException.class)
							.hasMessageContaining("Resource not found. Id:", product.getId());

		verify(productRepository, times(1)).findById(2L);
		
	}
	
	
	@Test
	public void findAll_ProductAllList_ExpectedSucess() {
		
		var pageable = ScenarioFactory.newPageable();
		var productPage = ScenarioFactory.newPage();
		var nome= "generic";
		
		when(productRepository.findByNome(nome, pageable)).thenReturn(productPage);
		
		var productAll = productService.findAll(nome, pageable);
		
		assertNotNull(productAll);

		verify(productRepository, times(1)).findByNome(nome, pageable);
		
	}
	
	@Test
	public void delete_WhenReceiveValidIdDeleteProduct_ExpectedSucess() {
		var product = ScenarioFactory.newProduct();
		var productOptional = ScenarioFactory.newOptionalProduct();
	
		when(productRepository.findById(eq(product.getId()))).thenReturn(productOptional);
		
		productService.delete(1L);
		rabbitDeleteQueue.sendMessage(product);
		
		verify(productRepository, times(1)).delete(product);
		
	}
	
	@Test
	public void delete_WhenReceiveInvalidIdNotDeleteProduct_ExpectedThrownException() {
			var product = ScenarioFactory.newProduct();		
			 when(productRepository.findById(2L)).thenReturn(Optional.empty());
			
	   assertThatThrownBy(()-> productService.delete(2L))
			   				.isInstanceOf(ResourceNotFoundException.class)
							.hasMessageContaining("Resource not found. Id:", product.getId());

		verify(productRepository, times(1)).findById(2L);
	}
}

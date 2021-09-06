package io.guilhermefasilva.microservice.product.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
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
import org.springframework.data.domain.Page;

import io.guilhermefasilva.feature.ScenarioFactory;
import io.guilhermefasilva.microservice.product.domain.models.Product;
import io.guilhermefasilva.microservice.product.exception.ResourceNotFoundException;
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
		public void save_SaveSProduct_ExpectedSucess() {
			var product = ScenarioFactory.newProduct();
			var productRequest = ScenarioFactory.newProductRequest();
			var productResponse = ScenarioFactory.newProductDtoResponse();
			
			    given(productRepository.save(product)).willReturn(product);
			    when(productService.save(productRequest)).thenReturn(productResponse);
			   
			    verify(this.productRepository, times(1)).save(any());
		}
		
		
		@Test
		public void findById_ProductFind_ExpectedSucess() {
			var product = ScenarioFactory.newProduct();
			Optional<Product> products = Optional.of(product);
			when(productRepository.findById(eq(product.getId()))).thenReturn(products);
			
			this.productService.findById(1L);
			
			verify(this.productRepository, times(1)).findById(any());
		}
		
		
		
		@Test
		public void update_UpdateProductFindById_ExpectedSucess() {
				var product = ScenarioFactory.newProduct();
				var productUpdate = ScenarioFactory.newProductUpdate();
				
			Optional<Product> products = Optional.of(product);
			when(productRepository.findById(eq(product.getId()))).thenReturn(products);
			when(productRepository.save(any(Product.class))).thenReturn(product);

			this.productService.update(1L, productUpdate);

			verify(this.productRepository, times(1)).save(any(Product.class));
			
		}
		
		
		
		@Test
		public void findAll_ProductAllList_ExpectedSucess() {
			
			Page<Product> productPage = ScenarioFactory.newPageProduct();
			
			when(this.productRepository.findByNome(any(), any())).thenReturn(productPage);

			this.productService.findAll(any(),any());

			verify(this.productRepository, times(1)).findByNome(any(), any());
			
		}
		
		@Test
		public void delete_ProductDeleteFindById_ExpectedSucess() {
			var product = ScenarioFactory.newProduct();
			
			Optional<Product> products = Optional.of(product);
			when(productRepository.findById(eq(product.getId()))).thenReturn(products);
			doNothing().when(productRepository).delete(any(Product.class));

			this.productService.delete(1L);
			this.rabbitDeleteQueue.sendMessage(products);

			verify(this.productRepository, times(1)).delete(any(Product.class));
			verify(this.rabbitDeleteQueue, times(1)).sendMessage(products);
		}
		
		@Test
		public void delete_ProductDeleteNotFindById_ExpectedThrownException() {
				var product = ScenarioFactory.newProduct();		
				given(productRepository.findById(eq(product.getId()))).willThrow(ResourceNotFoundException.class);
				doNothing().when(productRepository).delete(any(Product.class));
				
				
		   assertThatThrownBy(()-> productService.delete(any()))
				   				.isInstanceOf(ResourceNotFoundException.class)
								.hasMessageContaining("Resource not found. Id:", product.getId());

			verify(this.productRepository, times(1)).findById(any());
		}
		
		
		@Test
		public void findById_ProductNotFindById_ExpectedThrownException() {
				var product = ScenarioFactory.newProduct();
			when(productRepository.findById(eq(product.getId()))).thenThrow(ResourceNotFoundException.class);

			assertThatThrownBy(()->productService.findById(2L))
								.isInstanceOf(ResourceNotFoundException.class)
								.hasMessageContaining("Resource not found. Id:", product.getId());

			verify(this.productRepository, times(1)).findById(any());
								
		}
		
		@Test
		public void update_ProductUpdateNotFindById_ExpectedThrownException() {
			var product = ScenarioFactory.newProduct();	
			var productUpdate = ScenarioFactory.newProductUpdate();
			
			when(productRepository.findById(2L)).thenThrow(ResourceNotFoundException.class);

			assertThatThrownBy(()->productService.update(any(), productUpdate))
								.isInstanceOf(ResourceNotFoundException.class)
								.hasMessageContaining("Resource not found. Id:", product.getId());

			verify(this.productRepository, times(1)).findById(any());
			
		}
}

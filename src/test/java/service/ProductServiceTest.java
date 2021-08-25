package service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoResponse;
import io.guilhermefasilva.microservice.product.repository.ProductRepository;
import io.guilhermefasilva.microservice.product.service.ProductService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
	
	
	@InjectMocks
	private ProductService productService;
	@Mock
	private ProductRepository productRepository;
	@Mock
	private ProductDtoResponse productResponse;
	
	
	
	
	
	
	
	@Test
	@DisplayName("Deve retornar o status code 200 quando o id pesquisado for válido.")
	public void buscarPorIdProdutoTest() {
		Mockito.when(productService.findById(5L)).thenReturn(productResponse);
		
		
	}
	
}

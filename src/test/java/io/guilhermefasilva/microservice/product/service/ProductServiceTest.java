package io.guilhermefasilva.microservice.product.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

import io.guilhermefasilva.microservice.product.repository.ProductRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@MockBean
	private ProductRepository productRepository;
	
	@InjectMocks
	private ProductService productservice;
	
	
	
	}

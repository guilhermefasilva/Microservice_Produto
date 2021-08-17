package io.guilhermefasilva.microservice.product.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoRequest;
import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoResponse;
import io.guilhermefasilva.microservice.product.domain.models.Product;
import io.guilhermefasilva.microservice.product.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public ProductDtoResponse save(ProductDtoRequest productDtoRequest) {
		Product produto = modelMapper.map(productDtoRequest, Product.class); 
				productRepository.save(produto);
			return modelMapper.map(produto, ProductDtoResponse.class);
	}
	
	

}

package io.guilhermefasilva.microservice.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoRequest;
import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoRequestUpdate;
import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoResponse;
import io.guilhermefasilva.microservice.product.domain.enums.ProductStatus;
import io.guilhermefasilva.microservice.product.domain.models.Product;
import io.guilhermefasilva.microservice.product.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public ProductDtoResponse save(ProductDtoRequest productDtoRequest) {
		Product product = modelMapper.map(productDtoRequest, Product.class); 
				productRepository.save(product);
			return modelMapper.map(product, ProductDtoResponse.class);
	}
	
	public List<ProductDtoResponse> findAll() {
		List<Product> product = productRepository.findAll();
		return product.stream()
				.map(p -> modelMapper.map(p, ProductDtoResponse.class))
				.collect(Collectors.toList());
	}
	
	public ProductDtoResponse findById(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Id não encontrado"));
		return modelMapper.map(product, ProductDtoResponse.class);
	}
	
	
	public ProductDtoResponse update(Long id, ProductDtoRequestUpdate productUpdate) {
		Product product = productRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Id não encontrado"));
		
		product.setDescricao(productUpdate.getDescricao());
		
		this.productRepository.save(product);
		return modelMapper.map(product, ProductDtoResponse.class);
	}
	

}

package io.guilhermefasilva.microservice.product.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoRequest;
import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoRequestUpdate;
import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoResponse;
import io.guilhermefasilva.microservice.product.domain.models.Product;
import io.guilhermefasilva.microservice.product.exception.ResourceNotFoundException;
import io.guilhermefasilva.microservice.product.repository.ProductRepository;
import io.guilhermefasilva.microservice.product.sender.ProductDeletedQueueSender;

@Service
public class ProductService  {
	

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductDeletedQueueSender rabbitmqDeleteQueueSender;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Transactional
	public ProductDtoResponse save(ProductDtoRequest productDtoRequest) {
		Product product = modelMapper.map(productDtoRequest, Product.class); 
				productRepository.save(product);
			return modelMapper.map(product, ProductDtoResponse.class);
	}
	
		
	public List<ProductDtoResponse> findAll(String nome, Pageable pageable){
		Page<Product> productPage = productRepository.findByNome(nome, pageable);
		List<Product> product =  productPage.getContent();
		return product.stream()
				.map(p -> modelMapper.map(p, ProductDtoResponse.class))
				.collect(Collectors.toList());
	}
	
	public ProductDtoResponse findById(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
		return modelMapper.map(product, ProductDtoResponse.class);
	}

	@Transactional
	public ProductDtoResponse update(Long id, ProductDtoRequestUpdate productUpdate){
		Product product = productRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException(id));
		product.setDescricao(productUpdate.getDescricao());
		this.productRepository.save(product);
		return modelMapper.map(product, ProductDtoResponse.class);
	}
	
	@Transactional
	public void delete(Long id) {
		Product produto = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
		this.productRepository.delete(produto);
		this.rabbitmqDeleteQueueSender.sendMessage(produto);
		
	}
	
}

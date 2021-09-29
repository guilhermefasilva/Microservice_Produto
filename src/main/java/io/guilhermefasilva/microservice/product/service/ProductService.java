package io.guilhermefasilva.microservice.product.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoRequest;
import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoRequestUpdate;
import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoResponse;
import io.guilhermefasilva.microservice.product.domain.models.Product;
import io.guilhermefasilva.microservice.product.exception.ResourceNotFoundException;
import io.guilhermefasilva.microservice.product.repository.ProductRepository;
import io.guilhermefasilva.microservice.product.sender.ProductDeletedQueueSender;
import lombok.extern.slf4j.Slf4j;


@Slf4j
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
				var productSaved = productRepository.save(product);
				log.info("[{}]",product);
			return modelMapper.map(productSaved, ProductDtoResponse.class);
	}
	
		
	public List<ProductDtoResponse> findAll(String nome, Pageable pageable){
		Page<Product> productPage = productRepository.findByNome(nome, pageable);
		List<Product> product =  productPage.getContent();
		log.info("[{}]",product);
		return product.stream()
				.map(p -> modelMapper.map(p, ProductDtoResponse.class))
				.collect(Collectors.toList());
	}
	
	public ProductDtoResponse findById(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found: "+ id));
		log.info("[{}]", product);
		return modelMapper.map(product, ProductDtoResponse.class);
	}

	@Transactional
	public ProductDtoResponse update(Long id, ProductDtoRequestUpdate productUpdate){
		Product product = productRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Resource not found: "+id));
		modelMapper.map(productUpdate, product);
		this.productRepository.save(product);
		log.info("[{}]", product);
		return modelMapper.map(product, ProductDtoResponse.class);
	}
	
	@Transactional
	public void delete(Long id) throws JsonProcessingException{
		Product produto = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found: "+id));
		this.productRepository.delete(produto);
		log.info("[{}]",produto);
		this.rabbitmqDeleteQueueSender.sendMessage(produto);
		
	}
	
	 
}

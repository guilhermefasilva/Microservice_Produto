package io.guilhermefasilva.microservice.product.service.feature;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoRequest;
import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoRequestUpdate;
import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoResponse;
import io.guilhermefasilva.microservice.product.domain.models.Product;

public class ScenarioFactory {
	
	public static Product newProduct() {
		Product product = new Product();
		product.setId(1L);
		return product;
	}

	public static ProductDtoRequest newProductRequest() {
		return new ProductDtoRequest();
	}
	public static ProductDtoResponse newProductDtoResponse() {
		
		return new ProductDtoResponse(); 
	}

	public static ProductDtoRequestUpdate newProductUpdate() {
		return new ProductDtoRequestUpdate();
	}

	
	
	public static Pageable newPageable(){
		Pageable peageble = Pageable.ofSize(10); 
		return peageble;
	}
	
	public static Page<Product> newPage(){
		List<Product> product = new ArrayList<>();
		product.add(newProduct());
		Page<Product> productPage = new PageImpl<>(product);
		return  productPage;
	}

}

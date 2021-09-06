package io.guilhermefasilva.feature;

import org.springframework.data.domain.Page;

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
	
	public static ProductDtoRequestUpdate newProductUpdate() {
		return new ProductDtoRequestUpdate();
	}

	public static  Page<Product> newPageProduct() {
		return newPageProduct();
	}
	
	public static ProductDtoResponse newProductDtoResponse() {
		return new ProductDtoResponse();
	}
	
	
	
}

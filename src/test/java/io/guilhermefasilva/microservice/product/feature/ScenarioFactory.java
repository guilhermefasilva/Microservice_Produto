package io.guilhermefasilva.microservice.product.feature;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoRequest;
import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoRequestUpdate;
import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoResponse;
import io.guilhermefasilva.microservice.product.domain.models.Product;

public class ScenarioFactory {
	
	public static Product newProduct() {
		var product = new Product();
		product.setId(1L);
		product.setNome(newProductRequest().getNome());
		product.setMarca(newProductRequest().getMarca());
		product.setDescricao(newProductRequest().getDescricao());
		product.setPreco(newProductRequest().getPreco());
		return product;
	}

	public static ProductDtoRequest newProductRequest() {
		var productRequest = new ProductDtoRequest();
		productRequest.setNome("Anador");
		productRequest.setMarca("Generico");
		productRequest.setDescricao("Cartela com 10 comprimidos");
		productRequest.setPreco(new BigDecimal(12.00));
		return productRequest;
	}
	
	
	
	public static ProductDtoResponse newProductDtoResponse() {
		var productResponse = new ProductDtoResponse(); 
		productResponse.setId(newProduct().getId());
		productResponse.setNome(newProduct().getNome());
		productResponse.setMarca(newProduct().getMarca());
		productResponse.setDescricao(newProduct().getDescricao());
		productResponse.setPreco(newProduct().getPreco());
		return productResponse;
	}
	
	

	public static ProductDtoRequestUpdate newProductRequestDtoUpdate() {
		var productUpdate = new ProductDtoRequestUpdate();
		productUpdate.setDescricao("caixa com 50 compimidos");
		return productUpdate;		
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
	
	
	public static Optional<Product> newOptionalProduct(){
		return Optional.of(newProduct());
	}
	

}

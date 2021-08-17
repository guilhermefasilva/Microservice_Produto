package io.guilhermefasilva.microservice.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoRequest;
import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoResponse;
import io.guilhermefasilva.microservice.product.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	
	@Autowired
	private ProductService productService;
	
	@PostMapping
	public ResponseEntity<ProductDtoResponse> create(ProductDtoRequest produto){
			ProductDtoResponse produtoResponse =  productService.save(produto);
		return ResponseEntity.ok().body(produtoResponse);
	}
	
	

}

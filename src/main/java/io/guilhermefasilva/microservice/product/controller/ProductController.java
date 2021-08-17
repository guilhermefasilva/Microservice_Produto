package io.guilhermefasilva.microservice.product.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoRequest;
import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoRequestUpdate;
import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoResponse;
import io.guilhermefasilva.microservice.product.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	
	@Autowired
	private ProductService productService;
	
	@PostMapping
	@Transactional
	public ResponseEntity<ProductDtoResponse> create(@RequestBody ProductDtoRequest produto){
			ProductDtoResponse produtoResponse =  productService.save(produto);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
					.buildAndExpand(produtoResponse.getId()).toUri();
		return ResponseEntity.created(uri).body(produtoResponse);
	}
	
	@GetMapping
	public ResponseEntity<List<ProductDtoResponse>> getAll(){
		List<ProductDtoResponse> produtoResponse = productService.findAll();
		return ResponseEntity.ok().body(produtoResponse);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDtoResponse> getById(@PathVariable Long id){
		ProductDtoResponse produtoResponse = productService.findById(id);
		return ResponseEntity.ok().body(produtoResponse);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<ProductDtoResponse> update(@PathVariable Long id,
			@RequestBody ProductDtoRequestUpdate productUpdate){
		ProductDtoResponse productResponse = productService.update(id, productUpdate);
		return ResponseEntity.ok().body(productResponse);
		
	}
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Long id){
		productService.delete(id);
		return ResponseEntity.noContent().build();
	}

}

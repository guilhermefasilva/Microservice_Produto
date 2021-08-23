package io.guilhermefasilva.microservice.product.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping
	@ApiOperation(value = "Realiza o cadastro de produtos")
	@ApiResponses(value = {
						@ApiResponse(code = 201, message = "Requisição bem sucedida"),
						@ApiResponse(code = 400, message = "Requisição não atendida dados incorretos ou falta informações"),
						@ApiResponse(code = 500, message = "Sistema indisponivel")})
	
	public ResponseEntity<ProductDtoResponse> create(@Valid @RequestBody ProductDtoRequest produto){
			ProductDtoResponse produtoResponse =  productService.save(produto);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
					.buildAndExpand(produtoResponse.getId()).toUri();
		return ResponseEntity.created(uri).body(produtoResponse);
	}
	
	@GetMapping
	@ApiOperation(value = "Exibe produtos por pagnias")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Requisição bem sucedida"),
			@ApiResponse(code = 500, message = "Sistema Indisponivel")
	})
	public ResponseEntity<List<ProductDtoResponse>> getAllPage(@PageableDefault(page = 0, size = 5) Pageable pageable){
		List<ProductDtoResponse> productResponsePage = productService.findAll(pageable);
		return ResponseEntity.ok().body(productResponsePage);
	}
	
	
	@GetMapping("/page")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Requisição bem sucedida"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Sistema indisponivel")})
	public ResponseEntity<List<ProductDtoResponse>> getByName(String nome, Pageable pageable){
		List<ProductDtoResponse> productResponse = productService.findByName(nome, pageable);
		return ResponseEntity.ok().body(productResponse);
	}
	
	
	
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Exibe um produto atraves de um id válido")
	@ApiResponses(value= {
				@ApiResponse(code = 200, message = "Requisição bem sucedida"),
				@ApiResponse(code = 400, message = "Requisição não atendida, dados incorretos ou falta informações"),
				@ApiResponse(code = 404, message = "Produto não encontrado")})
	
	public ResponseEntity<ProductDtoResponse> getById(@PathVariable Long id ){
		ProductDtoResponse produtoResponse = productService.findById(id);
		return ResponseEntity.ok().body(produtoResponse);
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Atualizar informações de um produto")
	@ApiResponses(value = {
					@ApiResponse(code = 200, message = "Requisição bem sucedida" ),
					@ApiResponse(code = 400, message = "Requisição não atendida, dados incorretos ou falta informações"),
					@ApiResponse(code = 404, message = "Produto não encontrado"),
					@ApiResponse(code = 500, message = "Sistema indisponivel")})
	
	public ResponseEntity<ProductDtoResponse> update(@Valid @PathVariable Long id,
			@RequestBody ProductDtoRequestUpdate productUpdate){
		ProductDtoResponse productResponse = productService.update(id, productUpdate);
		return ResponseEntity.ok().body(productResponse);
		
	}
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir um produto da base de dados.")
	@ApiResponses(value= {
						@ApiResponse(code = 204, message = "Requisição bem sucedida, não há recurso a ser exibido"),
						@ApiResponse(code = 404, message = "Produto não encontrado"),
						@ApiResponse(code = 500, message = "Sistema indisponivel")})
	
	public ResponseEntity<?> delete(@PathVariable Long id){
		productService.delete(id);
		return ResponseEntity.noContent().build();
	}

}

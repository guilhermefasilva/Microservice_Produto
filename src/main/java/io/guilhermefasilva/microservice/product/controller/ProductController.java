package io.guilhermefasilva.microservice.product.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;

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
	
	public ResponseEntity<ProductDtoResponse> create(@Valid @RequestBody ProductDtoRequest produtoRequest){
			ProductDtoResponse produtoResponse =  productService.save(produtoRequest);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
					.buildAndExpand(produtoResponse.getId()).toUri();
		return ResponseEntity.created(uri).body(produtoResponse);
	}
	 
	@GetMapping
	@ApiOperation(value = "Realiza exibição de produtos com filtros e paginação")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Requisição bem sucedida"),
			@ApiResponse(code = 500, message = "Sistema indisponivel")})
	public ResponseEntity<List<ProductDtoResponse>> getAll(@RequestParam(required = false, defaultValue = "%") String nome,
			@PageableDefault(sort="nome", direction = Direction.ASC, page = 0, size = 5) Pageable pageable){
		List<ProductDtoResponse> productResponse = productService.findAll(nome, pageable);
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
	
	public ResponseEntity<ProductDtoResponse> update(@PathVariable Long id,
			@RequestBody  @Valid ProductDtoRequestUpdate productUpdate){
		ProductDtoResponse productResponse = productService.update(id, productUpdate);
		return ResponseEntity.ok().body(productResponse);
		
	}
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Excluir um produto da base de dados.")
	@ApiResponses(value= {
						@ApiResponse(code = 204, message = "Requisição bem sucedida, não há recurso a ser exibido"),
						@ApiResponse(code = 404, message = "Produto não encontrado"),
						@ApiResponse(code = 500, message = "Sistema indisponivel")})
	
	public ResponseEntity<?> delete(@PathVariable Long id) throws JsonProcessingException {
		productService.delete(id);
		return ResponseEntity.noContent().build();
	}

	
	
}

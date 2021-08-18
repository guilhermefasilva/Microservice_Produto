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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	
	@Autowired
	private ProductService productService;
	
	@PostMapping
	@Transactional
	@ApiOperation(
				httpMethod = "POST",
				value = "Cadastrar um produto na base de dados",
				response = ProductDtoResponse.class,
				nickname = "createProduct",
				notes = "Os valores devem ser fornecidos no corpo da requisição em formato Json, com excessão do campo preco que "
						+ "\n por padrão é formato numérico, os demais campos são strings. O campo Status é um tipo enum e aceita"
						+ "\n somente dois valores, ATIVO e INATIVO."
			
			
			)
	@ApiResponses(value = {
						@ApiResponse(code = 201, message = "Created Resource, requisição bem sucedida"),
						@ApiResponse(code = 400, message = "Bad Request, requisição não atendida ")
	}	
			)
	
	public ResponseEntity<ProductDtoResponse> create(@RequestBody ProductDtoRequest produto){
			ProductDtoResponse produtoResponse =  productService.save(produto);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
					.buildAndExpand(produtoResponse.getId()).toUri();
		return ResponseEntity.created(uri).body(produtoResponse);
	}
	
	@GetMapping
	@ApiOperation(
				httpMethod = "GET",
				value = "Listar todos os produtos cadastrados na base de dados",
				response = ProductDtoResponse.class,
				nickname = "findAllProducts",
				notes="Não há parametros a serem repassados na URI, somente uma requisição do tipo GET,"
						+ "\n fará com que obtenha a listagem de todos os produtos cadastrados na base de dados"
			
			)
	@ApiResponses(value = {
					@ApiResponse(code = 200, message = "ok, Requisição bem sucedida"),
					@ApiResponse(code = 404, message = "Not Found, recurso não encontrado")
	}
			)
	
	public ResponseEntity<List<ProductDtoResponse>> getAll(){
		List<ProductDtoResponse> produtoResponse = productService.findAll();
		return ResponseEntity.ok().body(produtoResponse);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(
				httpMethod = "GET",
				value = "Listar um produto especifico através de seu identificador ID",
				response = ProductDtoResponse.class,
				nickname = "getByIdProduct",
				notes="Como parametro na URI deverá ser repassado um ID de um produto especifico para que seja feito a consulta"
						+ "\n na base de dados, como resposta caso ID seja valido será exibido os dados do produto consultado"
						+ "\n no corpo da resposta, a resposta estará no formado Json"
			)
	@ApiResponses(value= {
				@ApiResponse(code=200, message = "ok, requisição bem sucedida"),
				@ApiResponse(code = 404, message = "Not Found, recurso não encontrado")
	}
			)
	
	public ResponseEntity<ProductDtoResponse> getById(@PathVariable @ApiParam("id do produto") Long id ){
		ProductDtoResponse produtoResponse = productService.findById(id);
		return ResponseEntity.ok().body(produtoResponse);
	}
	
	@PutMapping("/{id}")
	@Transactional
	@ApiOperation(
			httpMethod = "PUT",
			value = "Atualizar informações de um produto através de um id informado",
			response = ProductDtoResponse.class,
			nickname = "updateProduct",
			notes="Os dados de um produto podem ser atualizado como descrição e status, para fazer a atualização "
					+ "\n deverá ser informado o id do produto que receberá a atualização, o id deverá ser repassado"
					+ "\n na URI do endpoint"
			)
	@ApiResponses(value = {
					@ApiResponse(code = 200, message = "ok, requisição bem sucedida" ),
					@ApiResponse(code = 404, message = "Not Found, recusro não encontrado")
	}
			)
	
	public ResponseEntity<ProductDtoResponse> update(@PathVariable Long id, @ApiParam("id do produto")
			@RequestBody ProductDtoRequestUpdate productUpdate){
		ProductDtoResponse productResponse = productService.update(id, productUpdate);
		return ResponseEntity.ok().body(productResponse);
		
	}
	@DeleteMapping("/{id}")
	@Transactional
	@ApiOperation(
			httpMethod = "DELETE",
			value = "Excluir um produto da base de dados.",
			nickname = "deleteProduct",
			notes = "Um id de um produto deverá ser informado na URI para que seja excluido,"
					+ "\n o ID sendo válido a exclusão será efetuada com sucesso"
			)
	@ApiResponses(value= {
						@ApiResponse(code = 204, message = "No Content, requisição ben sucedida, não há recurso a ser exibido"),
						@ApiResponse(code = 404, message = "Not Found, recurso não encontrado" )
						
	}
			)
	public ResponseEntity<?> delete(@PathVariable @ApiParam("id do produto") Long id){
		productService.delete(id);
		return ResponseEntity.noContent().build();
	}

}

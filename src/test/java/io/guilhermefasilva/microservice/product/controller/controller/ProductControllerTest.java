package io.guilhermefasilva.microservice.product.controller.controller;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import io.guilhermefasilva.microservice.product.controller.ProductController;
import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoRequest;
import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoRequestUpdate;
import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoResponse;
import io.guilhermefasilva.microservice.product.service.ProductService;


@WebMvcTest(ProductController.class)
class ProductControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductService productService;

 
	@Test
	@DisplayName("Testar a pesquisa de todos os produtos retornando a lista, o status de retorno deverá ser ok")
	public void testarListaDeTodosOsProdutos() throws Exception {
		List<ProductDtoResponse> productResponse = new ArrayList<>();
		Mockito.when(productService.findAll(null, null)).thenReturn(productResponse);
		this.mockMvc.perform(get("/products")).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@DisplayName("Testar a pesquisa de um produto pelo Id, o status de retorno deverá ser ok")
	public void testarUmProdutoPeloId() throws Exception {
		ProductDtoResponse produtoResponse = mock(ProductDtoResponse.class);
		Mockito.when(productService.findById(16L)).thenReturn(produtoResponse);
		this.mockMvc.perform(get("/products/")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@DisplayName("Testar a criação de um novo produto, o status de retorno deverá ser created")
	public void testarCadastroDeProduto() throws Exception {
		ProductDtoRequest produtoRequest = mock(ProductDtoRequest.class);
		Mockito.when(productService.save(produtoRequest)).thenReturn(mock(ProductDtoResponse.class));
		this.mockMvc.perform(post("/products")).andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	@DisplayName("Testar a atualização de um produto, o status de retorno deverá ser ok")
	public void testarAtualizacaoDeProduto() throws Exception {
		ProductDtoRequestUpdate produtoUpdate = mock(ProductDtoRequestUpdate.class);
		Mockito.when(productService.update(1L, produtoUpdate)).thenReturn( mock(ProductDtoResponse.class)) ;
		this.mockMvc.perform(put("/products")).andExpect(MockMvcResultMatchers.status().isOk());
	}

}

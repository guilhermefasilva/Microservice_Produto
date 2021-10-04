package io.guilhermefasilva.microservice.product.controller;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.cucumber.spring.CucumberContextConfiguration;
import io.guilhermefasilva.microservice.product.domain.models.Product;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@CucumberContextConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductControllerCucumberTest {

	@LocalServerPort
	private int port;
	private RestTemplate restTemplate = new RestTemplate();
	private String urlDefault = "http://localhost";
	private String subDomain = "/products";
	private Long idProduct;
	
//	@Dado("que faça uma solicitação para cadastrar um produto")
//	public void que_faça_uma_solicitação_para_cadastrar_um_produto() {
//	 String url = urlDefault +":"+ port + subDomain;
//		Product product = restTemplate.getForObject(url, Product.class);
//	  log.info("{}",product);
//	  assertNotNull(product);
//	}

	@Quando("^enviar id (.*), nome (.*), marca (.*), descricao (.*) e preco (.*)$")
	public void enviar_nome_doril_marca_generico_descricao_cartela_com_e_preco
				(Long id, String nome, String marca, String descricao, BigDecimal preco) {
		String url = urlDefault +":"+ port + subDomain;
	  Product newProduct = new Product();
	  newProduct.setId(id);
	  newProduct.setNome(nome);
	  newProduct.setMarca(marca);
	  newProduct.setDescricao(descricao);
	  newProduct.setPreco(preco);
	  Product product = restTemplate.postForObject(url, newProduct, Product.class);
	  idProduct = product.getId();
	  log.info("{}",product);
	  assertNotNull(product);
		
	}
	

	@Entao("é retornado um novo produto cadastrado")
	public void é_retornado_um_novo_produto_cadastrado() {
		String url = urlDefault +":"+ port + subDomain;
		String newUrl = url+ "/" + idProduct;
		Product product = restTemplate.getForObject(newUrl, Product.class);
		log.info("{}",product);
		assertNotNull(product);
		
		
	}
	

}

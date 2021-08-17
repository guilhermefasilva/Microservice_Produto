package io.guilhermefasilva.microservice.product.domain.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductDtoRequest {
	
	private String nome;
	private String marca;
	private String descricao;
	private BigDecimal preco;
	private String status;
	

}

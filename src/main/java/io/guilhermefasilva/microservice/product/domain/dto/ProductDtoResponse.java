package io.guilhermefasilva.microservice.product.domain.dto;

import lombok.Data;

@Data
public class ProductDtoResponse {
	
	private Long id;
	private String nome;
	private String marca;
	private String descricao;
	//private String preco;
	private String status;

}

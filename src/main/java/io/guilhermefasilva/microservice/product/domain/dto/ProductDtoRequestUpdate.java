package io.guilhermefasilva.microservice.product.domain.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProductDtoRequestUpdate implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String descricao;
	private String Status;
	

}

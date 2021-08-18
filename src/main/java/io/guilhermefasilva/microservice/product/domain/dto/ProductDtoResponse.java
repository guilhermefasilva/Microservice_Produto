package io.guilhermefasilva.microservice.product.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDtoResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String marca;
	private String descricao;
	private BigDecimal preco;
	private String status;

}

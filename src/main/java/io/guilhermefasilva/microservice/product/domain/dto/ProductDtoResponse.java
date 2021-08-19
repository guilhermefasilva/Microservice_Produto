package io.guilhermefasilva.microservice.product.domain.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDtoResponse {
	

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example ="Teclado Luminoso")
	private String nome;
	@ApiModelProperty(example = "Dell")
	private String marca;
	@ApiModelProperty(example = "Teclado sem fio com bateria recarregavel e teclas luminosas")
	private String descricao;
	@ApiModelProperty(example = "250.00")
	private BigDecimal preco;
	@ApiModelProperty(example = "Ativo")
	private String status;

}

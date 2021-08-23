package io.guilhermefasilva.microservice.product.domain.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDtoResponse {
	

	@ApiModelProperty(notes="Id do produto",example = "1")
	private Long id;
	
	@ApiModelProperty(notes="Nome do produto", example ="Teclado Luminoso")
	private String nome;
	@ApiModelProperty(notes="Marca do produto",example = "Dell")
	private String marca;
	@ApiModelProperty(notes="Descrição do produto", example = "Teclado sem fio com bateria recarregavel e teclas luminosas")
	private String descricao;
	@ApiModelProperty(notes="Preço do produto",example = "250.00")
	private BigDecimal preco;
	

}

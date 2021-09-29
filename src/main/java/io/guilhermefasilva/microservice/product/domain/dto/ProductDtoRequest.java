package io.guilhermefasilva.microservice.product.domain.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDtoRequest  {
	
	
	@ApiModelProperty(value = "Nome do Produto não pode ficar em branco",example =  "Anador",required = true)
	@NotEmpty(message = "{field.name.not.empty}")
	private String nome;
	
	@ApiModelProperty(value="Marca do Produto deve ser preenchida corretamente", example = "Generico", required = true)
	@NotEmpty(message = "{field.marca.not.empty}")
	private String marca;
	
	@ApiModelProperty(value ="Descrição do produto não pode ficar em branco", example = "Caixa com 40 comprimidos", required=true)
	@NotEmpty(message = "{field.descricao.not.empty}")
	private String descricao;
	
	@ApiModelProperty(value = "Preço do produto, preço unitário do produto", example = "20.00", required = true)
	@NotNull(message = "{field.preco.not.null}")
	@PositiveOrZero(message = "{field.preco.positive}")
	private BigDecimal preco;
	
	

}

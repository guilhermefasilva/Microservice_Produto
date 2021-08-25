package io.guilhermefasilva.microservice.product.domain.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDtoRequest  {
	
	
	@ApiModelProperty(value = "Nome do Produto não pode ser nulo",example =  "Mouse",required = true)
	@NotEmpty(message = "{field.not.empty}")
	private String nome;
	
	@ApiModelProperty(value="Marca do Produto", example = "LogginTech", required = true)
	@NotNull(message = "{field.not.null}")
	private String marca;
	
	@ApiModelProperty(value ="Descrição do produto", example = "Mouse com fio USB 2.0")
	@NotEmpty(message = "{field.not.empty}")
	private String descricao;
	
	@ApiModelProperty(value = "Preço do produto", example = "80.00", required = true)
	@NotNull(message = "{field.not.null}")
	private BigDecimal preco;
	
	

}

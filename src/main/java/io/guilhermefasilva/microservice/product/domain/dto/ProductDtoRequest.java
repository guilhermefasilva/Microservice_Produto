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
	
	
	@ApiModelProperty(notes = "Nome do Produto",example =  "Mouse",required = true)
	@NotEmpty(message = "{name.notEmpty}")
	private String nome;
	
	@ApiModelProperty(notes="Marca do Produto", example = "LogginTech", required = true)
	@NotNull
	private String marca;
	
	@ApiModelProperty(notes ="Descrição do produto", example = "Mouse com fio USB 2.0")
	private String descricao;
	
	@ApiModelProperty(notes = "Preço do produto", example = "80.00", required = true)
	@NotNull
	private BigDecimal preco;
	
	

}

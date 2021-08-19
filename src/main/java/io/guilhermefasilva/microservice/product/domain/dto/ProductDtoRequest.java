package io.guilhermefasilva.microservice.product.domain.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDtoRequest  {
	
	
	@ApiModelProperty(example = "Mouse")
	private String nome;
	@ApiModelProperty(example = "LogginTech")
	private String marca;
	@ApiModelProperty(example = "Mouse com fio USB 2.0")
	private String descricao;
	@ApiModelProperty(example = "80.00")
	private BigDecimal preco;
	@ApiModelProperty(example = "Ativo")
	private String status;
	

}

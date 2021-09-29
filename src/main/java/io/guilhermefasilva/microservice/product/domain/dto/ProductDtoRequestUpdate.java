package io.guilhermefasilva.microservice.product.domain.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDtoRequestUpdate {
	

	
	@ApiModelProperty(value ="Descrição do produto não pode ficar em branco ou vazio",example = "Embalagem com 20 comprimidos", required = true)
	@NotEmpty(message = "{field.descricao.not.empty}")
	@NotBlank(message = "{field.descricao.not.blank}")
	private String descricao;
	
	@ApiModelProperty(value = "Marca do produto não pode ficar em branco ou vazio", example="Generico", required = true)
	@NotEmpty(message="{field.marca.not.empty}")
	private String marca;
	
	@ApiModelProperty(value ="Preco do produto não pode ser nulo",example = "2.99", required = true)
	@NotNull(message = "{field.preco.not.null}")
	private BigDecimal preco;
		
}

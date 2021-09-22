package io.guilhermefasilva.microservice.product.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

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
		
}

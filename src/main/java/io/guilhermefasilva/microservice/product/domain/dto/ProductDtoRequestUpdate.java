package io.guilhermefasilva.microservice.product.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDtoRequestUpdate {
	

	@ApiModelProperty(example = "Teclado sem fio com pilhas recarregaveis", required = true)
	private String descricao;
	
	@ApiModelProperty(example = "Inativo")
	private String status;
	

}

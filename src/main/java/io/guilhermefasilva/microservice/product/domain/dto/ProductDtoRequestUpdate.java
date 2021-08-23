package io.guilhermefasilva.microservice.product.domain.dto;

import com.sun.istack.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDtoRequestUpdate {
	

	@ApiModelProperty(notes ="Descrição do produto",example = "Teclado sem fio com pilhas recarregaveis", required = true)
	@NotNull
	private String descricao;
		
}

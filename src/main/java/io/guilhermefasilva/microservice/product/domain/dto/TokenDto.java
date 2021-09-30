package io.guilhermefasilva.microservice.product.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TokenDto {
	
	@ApiModelProperty(notes = "Token Gerado", example = "eyJhbGciOiJIUzI1NiJ9.(...)")
	private String token;
	
	@ApiModelProperty(notes="Tipo de autenticação do token ", example="Bearer")
	private String tipo;
}

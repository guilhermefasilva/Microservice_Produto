package io.guilhermefasilva.microservice.product.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TokenDto {

	private String token;
	private String tipo;
}

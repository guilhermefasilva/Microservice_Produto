package io.guilhermefasilva.microservice.product.domain.dto;

import javax.validation.constraints.NotBlank;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDto {

	@ApiModelProperty(value = "Login do cliente não pode ficar em branco", example="usuario@email.com", required = true)
	@NotBlank(message = "{field.username.not.empty}")
	private String username;
	
	@ApiModelProperty(value= "Senha não pode ficar em branco, informar senha valida", example="123456", required = true)
	@NotBlank(message="{field.password.not.empty}")
	private String password;
	
	public UsernamePasswordAuthenticationToken converter() {
		return new UsernamePasswordAuthenticationToken(username, password);
	}
}

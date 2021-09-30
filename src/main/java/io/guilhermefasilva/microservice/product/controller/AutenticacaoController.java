package io.guilhermefasilva.microservice.product.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.guilhermefasilva.microservice.product.domain.dto.LoginDto;
import io.guilhermefasilva.microservice.product.domain.dto.TokenDto;
import io.guilhermefasilva.microservice.product.service.AutenticacaoService;


@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
	
	@Autowired
	private AutenticacaoService autenticadorService;
	@Autowired
	private AuthenticationManager authManager;

	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@Valid @RequestBody LoginDto loginDto){
		UsernamePasswordAuthenticationToken dadosLogin = loginDto.converter();
		try {
			
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token = autenticadorService.gerarToken(authentication);
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
		
		
		
	}
	
	

}

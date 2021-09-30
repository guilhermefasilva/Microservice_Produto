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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/auth")
@Slf4j
public class AutenticacaoController {
	
	@Autowired
	private AutenticacaoService autenticadorService;
	@Autowired
	private AuthenticationManager authManager;

	@PostMapping
	@ApiOperation(value = "Gerar token de autenticação", authorizations = {@Authorization(value="jwtToken")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Requisição bem sucedida"),
			@ApiResponse(code = 400, message = "Requisição não atendida, dados incorretos ou falta informações"),
			@ApiResponse(code = 403, message = "Acesso não pertimitido, realize a autenticação"),
	})
	public ResponseEntity<TokenDto> autenticar(@Valid @RequestBody LoginDto loginDto){
		UsernamePasswordAuthenticationToken dadosLogin = loginDto.converter();
		log.info("Dados login: {}", dadosLogin);
		try {
			
			Authentication authentication = authManager.authenticate(dadosLogin);
			log.info("Autenticação: {}", authentication);
			String token = autenticadorService.gerarToken(authentication);
			log.info("Token gerado: {}",token);
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
		} catch (AuthenticationException e) {
			log.error("Token não gerado, autenticação falhou");
			return ResponseEntity.badRequest().build();
		}
		
		
		
	}
	
	

}

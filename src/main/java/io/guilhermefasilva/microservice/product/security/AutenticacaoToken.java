package io.guilhermefasilva.microservice.product.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.guilhermefasilva.microservice.product.domain.models.User;
import io.guilhermefasilva.microservice.product.repository.UserRepository;
import io.guilhermefasilva.microservice.product.service.AutenticacaoService;

public class AutenticacaoToken extends OncePerRequestFilter {
	
	private AutenticacaoService authService;
	
	private UserRepository userRepository;
	
	

	public AutenticacaoToken(AutenticacaoService authService, UserRepository userRepository) {
		this.authService = authService;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recuperarToken(request);
		boolean valido = authService.isTokenValido(token);
		
		if(valido) {
			autenticarCliente(token);
		}
		
		filterChain.doFilter(request, response);		
	}

	private void autenticarCliente(String token) {
		Long idUsuario = authService.getIdUsuario(token);
		User usuario = userRepository.findById(idUsuario).get();
		UsernamePasswordAuthenticationToken authentication = new 
				UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()) ;
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		
		if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		return token.substring(7, token.length());
	}
	
	

}

package io.guilhermefasilva.microservice.product.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.guilhermefasilva.microservice.product.domain.dto.LoginDto;
import io.guilhermefasilva.microservice.product.domain.models.User;
import io.guilhermefasilva.microservice.product.exception.ResourceNotFoundException;
import io.guilhermefasilva.microservice.product.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AutenticacaoService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Value("${product.jwt.expiration}")
	private String timeExpiration;
	
	@Value("${product.jwt.secret}")
	private String secret;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(()-> new ResourceNotFoundException("Dados Incorretos"));
		return user;
	}
	
	@Transactional
	public LoginDto createLogin(LoginDto loginDto) {
		User userLogin = modelMapper.map(loginDto, User.class);
		var loginSaved = userRepository.save(userLogin);
		return modelMapper.map(loginSaved, LoginDto.class);
	}
	
	public String gerarToken(Authentication authentication) {
		User userLogado = (User) authentication.getPrincipal();
		Date dateNow = new Date();
		Date dateExpiration = new Date(dateNow.getTime()+Long.parseLong(timeExpiration));
		
		return Jwts.builder()
				.setIssuer("Products")
				.setSubject(userLogado.getId().toString())
				.setIssuedAt(dateNow)
				.setExpiration(dateExpiration)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

	public boolean isTokenValido(String token) {

		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
		
		
		
	}

	public Long getIdUsuario(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
		
	}
	
	

	
}

package io.guilhermefasilva.microservice.product.exception.handler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.guilhermefasilva.microservice.product.exception.DatabaseException;
import io.guilhermefasilva.microservice.product.exception.MessageError;
import io.guilhermefasilva.microservice.product.exception.ResourceNotFoundException;
import io.guilhermefasilva.microservice.product.exception.StandardError;


@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {	
	
	

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
		String error = "Resource Not Found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> dataBase (DatabaseException e, HttpServletRequest request){
		String error = "Database Exception";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = new ArrayList<>();
		for (ObjectError err : ex.getBindingResult().getAllErrors()) {
			errors.add(err.getDefaultMessage());
		}
		MessageError messageError = new MessageError("Erro de Validação de Campos", errors);
		return new ResponseEntity<>(messageError, HttpStatus.BAD_REQUEST);
		
	}
	
	
}

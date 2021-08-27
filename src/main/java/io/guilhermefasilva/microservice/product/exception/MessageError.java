package io.guilhermefasilva.microservice.product.exception;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageError {
	
	private String error;
	private List<String> details;
}

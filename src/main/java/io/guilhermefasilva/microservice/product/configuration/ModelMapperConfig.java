package io.guilhermefasilva.microservice.product.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ModelMapperConfig {
	
	
	@Bean
	public ModelMapper modelMapper() {
			
		ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration()
			.setFieldMatchingEnabled(true)
			.setFieldAccessLevel(AccessLevel.PRIVATE);
		
		return modelMapper;
	}
	

}

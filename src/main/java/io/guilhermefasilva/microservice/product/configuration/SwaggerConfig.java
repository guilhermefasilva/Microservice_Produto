package io.guilhermefasilva.microservice.product.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
			
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.select()
				.apis(RequestHandlerSelectors.basePackage("io.guilhermefasilva.microservice.product"))
				.paths(PathSelectors.ant("/**"))
				.build()
				.apiInfo(apiInfo());
	}
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Microservice Product")
				.description("Microserviço de produtos, Cadastro, Alteração, Exclusão e consulta de produtos")
				.version("1.0.0")
				.license("Apache License Verson 2.0")
				.licenseUrl("\"https://www.apache.org/licenses/LICENSE-2.0\"")
				.contact(new Contact("Guilherme Assis", "https://github.com/guilhermefasilva" , "guilherme.assis@compasso.com.br"))
				.build();
	}
	
	

}

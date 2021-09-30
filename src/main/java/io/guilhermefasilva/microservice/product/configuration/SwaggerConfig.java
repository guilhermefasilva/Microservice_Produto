package io.guilhermefasilva.microservice.product.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.guilhermefasilva.microservice.product.domain.models.User;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
			
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.securityContexts(Arrays.asList(securityContext()))
				.securitySchemes(Arrays.asList(apiKey()))
				.select()
				.apis(RequestHandlerSelectors.basePackage("io.guilhermefasilva.microservice.product"))
				.paths(PathSelectors.ant("/**"))
				.build()
				.apiInfo(apiInfo())
				.ignoredParameterTypes(User.class);
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
	
	
	private ApiKey apiKey() {
		return new ApiKey("JWT", "Authorization", "header");
	}
	
	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).build();
	}

	private List<SecurityReference> defaultAuth(){
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
	}
	
}

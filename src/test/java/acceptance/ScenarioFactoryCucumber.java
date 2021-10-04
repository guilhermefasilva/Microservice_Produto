package acceptance;

import java.math.BigDecimal;

import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoRequestUpdate;
import io.guilhermefasilva.microservice.product.domain.models.Product;

public class ScenarioFactoryCucumber {
	
	public static Product newProduct() {
		Product product = new Product();
		product.setNome("Ibuprofeno");
		product.setMarca("Advil");
		product.setDescricao("Analgesico com 8 capsulas");
		product.setPreco(new BigDecimal(20.99));
		
		return product;
	}
	
	
	
	public static ProductDtoRequestUpdate newProductUpdate() {
		ProductDtoRequestUpdate productUpdate = new ProductDtoRequestUpdate();
		productUpdate.setDescricao("Analgesico com 10 capsulas");
		productUpdate.setMarca("Aspirina");
		productUpdate.setPreco(new BigDecimal(25.99));
		return productUpdate;
	}
	
}

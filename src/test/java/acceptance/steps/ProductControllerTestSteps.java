package acceptance.steps;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import acceptance.ScenarioFactoryCucumber;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.guilhermefasilva.microservice.product.ApiProdutoApplication;
import io.guilhermefasilva.microservice.product.domain.dto.ProductDtoRequestUpdate;
import io.guilhermefasilva.microservice.product.domain.models.Product;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CucumberContextConfiguration
@ContextConfiguration(classes = ApiProdutoApplication.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(value = SpringRunner.class)
public class ProductControllerTestSteps {

	@LocalServerPort
	private int port;
	private RestTemplate restTemplante = new RestTemplate();
	private String urlDefault = "http://localhost";
	private String subDomain = "/products";
	private Long idProduct;

	@Given("have the data to register a product")
	public void saved_GivenHaveTheData_ExpectedNewProduct() {
		var product = ScenarioFactoryCucumber.newProduct();
		log.info("New Product Data {}", product);
		assertNotNull(product);

	}

	@When("call the method to register the product")
	public void saved_WhenCallMethodPost_ExpectedSaveProduct() {
		String url = urlDefault + ":" + port + subDomain;
		var product = ScenarioFactoryCucumber.newProduct();
		product = restTemplante.postForObject(url, product, Product.class);
		idProduct = product.getId();
		log.info("Product saved sucess");
		assertNotNull(product);
		assertNotNull(idProduct);
	}

	@And("i can display the product by id")
	public void findById_WhenSearchingProductById_ExpectedReturnProduct() {
		var url = urlDefault + ":" + port + subDomain + "/" + idProduct;
		var product = restTemplante.getForObject(url, Product.class);
		log.info("Product id {} return success {}", idProduct, product);
		assertNotNull(product);
	}
	@And("i can list all products")
	public void findAll() {
		var url = urlDefault + ":" + port + subDomain;
		List<Product> productList = restTemplante.getForObject(url, List.class);
		log.info("List all products {}", productList);
		assertTrue(!productList.isEmpty());
	}

	@And("i can update product")
	public void update_WhenToEnterBYIdProduct_ExpectedUpdatedProduct() {
		var url = urlDefault + ":" + port + subDomain + "/" + idProduct;
		var productUpdate = ScenarioFactoryCucumber.newProductUpdate();
		restTemplante.put(url, productUpdate,ProductDtoRequestUpdate.class);
		assertNotNull(productUpdate);
	}

	@Then("i can delete product from repository")
	public void delete_whenRequestingDeleteProductGetById_ExpectedDeleteProduct() {
		var url = urlDefault + ":" + port + subDomain + "/" + idProduct;
		log.info("URL {}", url);
		restTemplante.delete(url);
		log.info("Product id {} deleted success", idProduct);

	}
	

}

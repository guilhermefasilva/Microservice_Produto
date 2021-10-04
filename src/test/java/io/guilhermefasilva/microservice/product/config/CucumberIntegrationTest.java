package io.guilhermefasilva.microservice.product.config;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/io/guilhermefasilva/microservice/product/feature")
public class CucumberIntegrationTest {
	

}

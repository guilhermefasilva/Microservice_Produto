@tag
Feature: as a customer I want to register and update products in data repository as well as list and delete
	@tag1		  
	Scenario: proposing that you want to do manipulations with a new product
			Given have the data to register a product
			When call the method to register the product
			And i can display the product by id
			And i can list all products
			And i can update product
			Then i can delete product from repository
			
			
package io.guilhermefasilva.microservice.product.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.guilhermefasilva.microservice.product.domain.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	
	

}

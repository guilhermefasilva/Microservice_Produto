package io.guilhermefasilva.microservice.product.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.guilhermefasilva.microservice.product.domain.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query("SELECT p FROM Product p where p.nome LIKE :nome")
	Page<Product> findByNome(String nome, Pageable pageable);
	

}

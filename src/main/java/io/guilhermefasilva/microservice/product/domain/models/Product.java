package io.guilhermefasilva.microservice.product.domain.models;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.guilhermefasilva.microservice.product.domain.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_product")
public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Campo não pode ser nulo")
	@Size(min = 3, max = 30)
	private String nome;
	
	@NotNull(message = "Campo não pode ser nulo")
	@Size(min = 3, max = 30)
	private String marca;
	
	@NotNull(message = "Campo não pode ser nulo")
	@Size(min = 8, max= 80)
	private String descricao;
	
	@NotNull(message = "Campo não pode ser nulo")
	private BigDecimal preco;
	
	@NotNull(message = "Campo não pode ser nulo")
	@Enumerated(EnumType.STRING)
	private ProductStatus status;
	

}

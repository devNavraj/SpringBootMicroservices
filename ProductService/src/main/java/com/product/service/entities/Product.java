package com.product.service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PRODUCT")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long productId;
	
	@Column(name = "PRODUCT_NAME")
	private String productName;
	
	@Column(name = "PRICE")
	private long price;
	
	@Column(name = "QUANTITY")
	private long quantity;
}

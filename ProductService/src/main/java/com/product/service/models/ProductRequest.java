package com.product.service.models;

import lombok.Data;

@Data
public class ProductRequest {

	private String name;
	private long price;
	private long quantity;
}

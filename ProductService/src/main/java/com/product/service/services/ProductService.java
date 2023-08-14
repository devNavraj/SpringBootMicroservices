package com.product.service.services;

import com.product.service.models.ProductRequest;
import com.product.service.models.ProductResponse;

public interface ProductService {

	long addProduct(ProductRequest productRequest);

	ProductResponse getProductById(long productId);

	void reduceQuantity(long productId, long quantity);

}

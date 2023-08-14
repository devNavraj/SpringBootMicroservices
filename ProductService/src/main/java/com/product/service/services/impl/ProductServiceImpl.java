package com.product.service.services.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.service.entities.Product;
import com.product.service.exceptions.ResourceNotFoundException;
import com.product.service.models.ProductRequest;
import com.product.service.models.ProductResponse;
import com.product.service.repositories.ProductRepository;
import com.product.service.services.ProductService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public long addProduct(ProductRequest productRequest) {
		log.info("Adding Product...");
		
		Product product = Product.builder()
							.productName(productRequest.getName())
							.quantity(productRequest.getQuantity())
							.price(productRequest.getPrice())
							.build();
		
		productRepository.save(product);
		
		log.info("Product Created");
		
		return product.getProductId();
	}

	@Override
	public ProductResponse getProductById(long productId) {
		log.info("Get the product for productId: {}", productId);
		
		Product product = productRepository
							.findById(productId)
							.orElseThrow(
									() -> new ResourceNotFoundException(
											"Product with given id not found!!!", 
											"PRODUCT_NOT_FOUND"
											)
							);
		ProductResponse	productResponse	= new ProductResponse();
		BeanUtils.copyProperties(product, productResponse);
		
		return productResponse;
	}

	@Override
	public void reduceQuantity(long productId, long quantity) {
		log.info("Reduce Quantity {} for Id: {}", quantity, productId);
		
		Product product = productRepository
							.findById(productId)
							.orElseThrow(
									() -> new ResourceNotFoundException(
											"Product with given Id not found!!!",
											"PRODUCT_NOT_FOUND"
											)
							);
				
		if(product.getQuantity() < quantity) {
			throw new ResourceNotFoundException(
					"Product does not have sufficient quantity.",
					"INSUFFICIENT_QUANTITY"
			);
		}
		
		product.setQuantity(product.getQuantity() - quantity);
		productRepository.save(product);
		
		log.info("Product Quantity updated Successfully");
	}

}

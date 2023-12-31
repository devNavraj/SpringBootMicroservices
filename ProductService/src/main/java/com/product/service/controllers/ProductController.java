package com.product.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.product.service.models.ProductRequest;
import com.product.service.models.ProductResponse;
import com.product.service.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@PreAuthorize("hasAuthority('Admin')")
	@PostMapping
	public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest) {
		
		long productId = productService.addProduct(productRequest);
		
		return new ResponseEntity<Long>(productId, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAuthority('Admin') || hasAuthority('Customer') || hasAuthority('Normal') || hasAuthority('SCOPE_internal')")
	@GetMapping("{id}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long productId) {
		
		ProductResponse productResponse = productService.getProductById(productId);
		
		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.OK);
	}
	
	@PutMapping("/reduceQuantity/{id}")
	public ResponseEntity<Void> reduceQuantity(
			@PathVariable("id") long productId,
			@RequestParam long quantity
	) {
		productService.reduceQuantity(productId, quantity);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}

package com.apiGateway.service.CloudGateway.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

	@GetMapping("/orderServiceFallBack")
	public String orderSerivceFallback() {
		return "Order Service is down!";
	}
	
	@GetMapping("/productServiceFallBack")
	public String productSerivceFallback() {
		return "Product Service is down!";
	}
	
	@GetMapping("/paymentServiceFallBack")
	public String paymentSerivceFallback() {
		return "Payment Service is down!";
	}
}

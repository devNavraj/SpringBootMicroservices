package com.order.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.service.models.OrderRequest;
import com.order.service.models.OrderResponse;
import com.order.service.service.OrderService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/orders")
@Log4j2
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PreAuthorize("hasAuthority('Customer')")
	@PostMapping("/placeOrder")
	public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest) {
		long orderId = orderService.placeOrder(orderRequest);
		log.info("Order Id: {}", orderId);
		return new ResponseEntity<Long>(orderId, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('Admin') || hasAuthority('Customer')")
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable long orderId) {
		OrderResponse orderResponse = orderService
											.getOrderDetails(orderId);
		return new ResponseEntity<>(orderResponse, HttpStatus.OK);
	}
}

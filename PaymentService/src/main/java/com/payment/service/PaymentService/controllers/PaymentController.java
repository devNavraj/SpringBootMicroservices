package com.payment.service.PaymentService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.service.PaymentService.models.PaymentRequest;
import com.payment.service.PaymentService.models.PaymentResponse;
import com.payment.service.PaymentService.services.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	
	@PostMapping
	public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest) {
		return new ResponseEntity<Long>(
				paymentService.doPayment(paymentRequest),
				HttpStatus.OK
		);
	}
	
	@GetMapping("/order/{orderId}")
	public ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable String orderId) {
		return new ResponseEntity<>(
				paymentService.getPaymentDetailsByOrderId(orderId), 
				HttpStatus.OK
		);
	}
}

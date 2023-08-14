package com.order.service.service.impl;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.order.service.entities.Order;
import com.order.service.exceptions.CustomException;
import com.order.service.external.client.PaymentService;
import com.order.service.external.client.ProductService;
import com.order.service.external.request.PaymentRequest;
import com.order.service.external.response.PaymentResponse;
import com.order.service.external.response.ProductResponse;
import com.order.service.models.OrderRequest;
import com.order.service.models.OrderResponse;
import com.order.service.repositories.OrderRepository;
import com.order.service.service.OrderService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public long placeOrder(OrderRequest orderRequest) {
		// Order Entity -> Save the data with Status Order Created
		// Product Service -> Block Products (Reduce the Quantity)
		// Payment Service -> Payments -> Success -> COMPLETE, Else CANCELLED
		log.info("Placing Order Request: {}", orderRequest);
		
		Order order = Order.builder()
						.amount(orderRequest.getTotalAmount())
						.orderStatus("CREATED")
						.productId(orderRequest.getProductId())
						.orderDate(Instant.now())
						.build();
		
		// Call the Product Service to reduce the quantity with REST API call using Feign Client
		productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());
		
		log.info("Creating Order with Status CREATED.");
		
		order = orderRepository.save(order);
		
		log.info("Calling Payment Service to complete the payment");
		
		PaymentRequest paymentRequest = PaymentRequest
											.builder()
											.orderId(order.getOrderId())
											.paymentMode(orderRequest.getPaymentMode())
											.amount(orderRequest.getTotalAmount())
											.build();
		
		String orderStatus = null;
		
		try {
			paymentService.doPayment(paymentRequest);
			log.info("Payment done successfully. Changing the Order Status to PLACED.");
			orderStatus = "PLACED";
		} catch (Exception e) {
			log.error("Error occured in payment.Changing the Order Status to PAYMENT_FAILED");
			orderStatus = "PAYMENT_FAILED";
		}
		
		order.setOrderStatus(orderStatus);
		orderRepository.save(order);

		log.info("Order Placed Successfully with Order Id: {}", order.getOrderId());
		
		return order.getOrderId();
	}

	@Override
	public OrderResponse getOrderDetails(long orderId) {
		
		log.info("Get order details for Order Id: {}", orderId);
		
		Order order = orderRepository
						.findById(orderId)
						.orElseThrow(() -> new CustomException(
								"Order not found for order Id: "+orderId,
								"NOT FOUND", 
								404)
						);
		
		log.info("Invoking Product Service to fetch the product for id: {}", order.getProductId());
		
		
		ProductResponse productResponse = restTemplate.getForObject(
														"http://PRODUCT-SERVICE/products/"+order.getProductId(),
														ProductResponse.class);
		
		log.info("Getting payment information from the Payment Service.");
		
		PaymentResponse paymentResponse = restTemplate
				.getForObject(
						"http://PAYMENT-SERVICE/payment/order/"+order.getOrderId(), 
						PaymentResponse.class);
		
		OrderResponse.ProductDetails productDetails 
				= OrderResponse.ProductDetails
				.builder()
				.productName(productResponse.getProductName())
				.productId(productResponse.getProductId())
				.build(); 
		
		OrderResponse.PaymentDetails paymentDetails
				= OrderResponse.PaymentDetails
				.builder()
				.paymentId(paymentResponse.getPaymentId())
				.status(paymentResponse.getStatus())
				.paymentMode(paymentResponse.getPaymentMode())
				.paymentDate(paymentResponse.getPaymentDate())
				.build();
		
		OrderResponse orderResponse = OrderResponse
											.builder()
											.orderId(order.getOrderId())
											.orderStatus(order.getOrderStatus())
											.amount(order.getAmount())
											.orderDate(order.getOrderDate())
											.productDetails(productDetails)
											.paymentDetails(paymentDetails)
											.build();
		return orderResponse;
	}

}

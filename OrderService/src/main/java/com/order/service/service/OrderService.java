package com.order.service.service;

import com.order.service.models.OrderRequest;
import com.order.service.models.OrderResponse;

public interface OrderService {

	long placeOrder(OrderRequest orderRequest);

	OrderResponse getOrderDetails(long orderId);

}

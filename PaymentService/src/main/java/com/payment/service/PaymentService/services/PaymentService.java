package com.payment.service.PaymentService.services;

import com.payment.service.PaymentService.models.PaymentRequest;
import com.payment.service.PaymentService.models.PaymentResponse;

public interface PaymentService {

	Long doPayment(PaymentRequest paymentRequest);

	PaymentResponse getPaymentDetailsByOrderId(String orderId);
}

package com.payment.service.PaymentService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payment.service.PaymentService.entities.TransactionDetails;

public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long>{

	TransactionDetails findByOrderId(long orderId);
}

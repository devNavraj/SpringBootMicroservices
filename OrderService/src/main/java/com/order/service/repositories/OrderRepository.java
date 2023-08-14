package com.order.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order.service.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}

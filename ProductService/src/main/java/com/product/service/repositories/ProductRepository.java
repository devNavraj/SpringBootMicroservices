package com.product.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.product.service.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}

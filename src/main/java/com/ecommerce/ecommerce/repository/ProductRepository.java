package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

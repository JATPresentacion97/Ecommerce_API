package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.model.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

    // Find all reviews for a specific product
    List<ProductReview> findByProduct(Product product);
}

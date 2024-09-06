package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.model.ProductReview;
import com.ecommerce.ecommerce.model.UserLogin;
import com.ecommerce.ecommerce.repository.ProductReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductReviewService {

    @Autowired
    private ProductReviewRepository productReviewRepository;

    /*
     * Get all reviews for a product
     */
    public List<ProductReview> getReviewsByProduct(Product product) {
        return productReviewRepository.findByProduct(product);
    }

    /*
     * Add a new review for a product
     */
    public ProductReview addReview(Product product, UserLogin user, int rating, String comment) {
        ProductReview review = new ProductReview(product, user, rating, comment);
        return productReviewRepository.save(review);
    }

    /*
     * Delete a review by ID
     */
    public void deleteReview(Long reviewId) {
        productReviewRepository.deleteById(reviewId);
    }
}

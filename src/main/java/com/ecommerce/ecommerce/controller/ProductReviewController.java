package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.model.ProductReview;
import com.ecommerce.ecommerce.model.UserLogin;
import com.ecommerce.ecommerce.service.ProductReviewService;
import com.ecommerce.ecommerce.service.ProductService;
import com.ecommerce.ecommerce.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/{productId}/reviews")
public class ProductReviewController {

    @Autowired
    private ProductReviewService productReviewService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserLoginService userLoginService;

    /*
     * Get all reviews for a product
     */
    @GetMapping
    public ResponseEntity<List<ProductReview>> getReviewsForProduct(@PathVariable Long productId) {
        Product product = productService.getProductById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        List<ProductReview> reviews = productReviewService.getReviewsByProduct(product);
        return ResponseEntity.ok(reviews);
    }

    /*
     * Add a new review to a product
     */
    @PostMapping
    public ResponseEntity<ProductReview> addReview(@PathVariable Long productId, @RequestParam String username,
                                                   @RequestParam int rating, @RequestParam String comment) {

        Product product = productService.getProductById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        UserLogin user = userLoginService.getUserLoginByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));

        ProductReview newReview = productReviewService.addReview(product, user, rating, comment);
        return new ResponseEntity<>(newReview, HttpStatus.CREATED);
    }

    /*
     * Delete a review by ID
     */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        productReviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}

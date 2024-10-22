package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.model.ProductReview;
import com.ecommerce.ecommerce.model.UserLogin;
import com.ecommerce.ecommerce.service.ProductReviewService;
import com.ecommerce.ecommerce.service.ProductService;
import com.ecommerce.ecommerce.service.UserLoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductReviewControllerTest {

    @InjectMocks
    private ProductReviewController productReviewController;

    @Mock
    private ProductReviewService productReviewService;

    @Mock
    private ProductService productService;

    @Mock
    private UserLoginService userLoginService;

    private Product product;
    private UserLogin user;
    private ProductReview review;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product(); // Assuming a no-arg constructor is available
        product.setId(1L);
        user = new UserLogin();
        user.setUsername("testuser");
        review = new ProductReview(); // Assuming a no-arg constructor is available
        review.setId(1L);
        review.setRating(5);
        review.setComment("Great product!");
    }

    @Test
    public void testGetReviewsForProduct() {
        List<ProductReview> reviews = new ArrayList<>();
        reviews.add(review);

        when(productService.getProductById(product.getId())).thenReturn(Optional.of(product));
        when(productReviewService.getReviewsByProduct(product)).thenReturn(reviews);

        ResponseEntity<List<ProductReview>> response = productReviewController.getReviewsForProduct(product.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reviews, response.getBody());
    }

    @Test
    public void testAddReview() {
        when(productService.getProductById(product.getId())).thenReturn(Optional.of(product));
        when(userLoginService.getUserLoginByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(productReviewService.addReview(any(Product.class), any(UserLogin.class), anyInt(), anyString())).thenReturn(review);

        ResponseEntity<ProductReview> response = productReviewController.addReview(product.getId(), user.getUsername(), review.getRating(), review.getComment());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(review, response.getBody());
    }

    @Test
    public void testDeleteReview() {
        Long reviewId = review.getId();

        // We do not need to return anything from the delete method
        doNothing().when(productReviewService).deleteReview(reviewId);

        ResponseEntity<Void> response = productReviewController.deleteReview(reviewId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

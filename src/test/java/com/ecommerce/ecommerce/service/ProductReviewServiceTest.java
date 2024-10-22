package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.model.ProductReview;
import com.ecommerce.ecommerce.model.UserLogin;
import com.ecommerce.ecommerce.repository.ProductReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductReviewServiceTest {

    @Mock
    private ProductReviewRepository productReviewRepository;

    @InjectMocks
    private ProductReviewService productReviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetReviewsByProduct() {
        Product product = new Product(); // Assuming you have an empty constructor
        ProductReview review1 = new ProductReview(product, new UserLogin(), 5, "Great product!");
        ProductReview review2 = new ProductReview(product, new UserLogin(), 4, "Good product.");
        List<ProductReview> reviews = Arrays.asList(review1, review2);

        when(productReviewRepository.findByProduct(product)).thenReturn(reviews);

        List<ProductReview> result = productReviewService.getReviewsByProduct(product);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(review1, result.get(0));
        verify(productReviewRepository, times(1)).findByProduct(product);
    }

    @Test
    void testAddReview() {
        Product product = new Product();
        UserLogin user = new UserLogin();
        int rating = 5;
        String comment = "Excellent product!";

        ProductReview review = new ProductReview(product, user, rating, comment);

        when(productReviewRepository.save(any(ProductReview.class))).thenReturn(review);

        ProductReview result = productReviewService.addReview(product, user, rating, comment);

        assertNotNull(result);
        assertEquals(rating, result.getRating());
        assertEquals(comment, result.getComment());
        verify(productReviewRepository, times(1)).save(any(ProductReview.class));
    }

    @Test
    void testDeleteReview() {
        Long reviewId = 1L;

        doNothing().when(productReviewRepository).deleteById(reviewId);

        productReviewService.deleteReview(reviewId);

        verify(productReviewRepository, times(1)).deleteById(reviewId);
    }
}

package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EmailControllerTest {

    @InjectMocks
    private EmailController emailController;

    @Mock
    private EmailService emailService;

    private String email;
    private String orderDetails;
    private String resetToken;
    private String promotionDetails;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        email = "testuser@example.com";
        orderDetails = "Order #12345";
        resetToken = "reset-token-123";
        promotionDetails = "20% off on your next purchase!";
    }

    @Test
    public void testSendOrderConfirmationEmail() {
        // Call the method to test
        ResponseEntity<Void> response = emailController.sendOrderConfirmationEmail(email, orderDetails);

        // Verify that the emailService method was called
        verify(emailService, times(1)).sendOrderConfirmationEmail(email, orderDetails);

        // Check the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody()); // No content
    }

    @Test
    public void testSendPasswordResetEmail() {
        // Call the method to test
        ResponseEntity<Void> response = emailController.sendPasswordResetEmail(email, resetToken);

        // Verify that the emailService method was called
        verify(emailService, times(1)).sendPasswordResetEmail(email, resetToken);

        // Check the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody()); // No content
    }

    @Test
    public void testSendPromotionalEmail() {
        // Call the method to test
        ResponseEntity<Void> response = emailController.sendPromotionalEmail(email, promotionDetails);

        // Verify that the emailService method was called
        verify(emailService, times(1)).sendPromotionalEmail(email, promotionDetails);

        // Check the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody()); // No content
    }
}

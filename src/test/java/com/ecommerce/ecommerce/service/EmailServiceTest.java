package com.ecommerce.ecommerce.service;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendSimpleMessage() {
        String to = "test@example.com";
        String subject = "Test Subject";
        String text = "Test message body";

        emailService.sendSimpleMessage(to, subject, text);

        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendOrderConfirmationEmail() {
        String to = "customer@example.com";
        String orderDetails = "Order ID: 12345";

        emailService.sendOrderConfirmationEmail(to, orderDetails);

        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendPasswordResetEmail() {
        String to = "user@example.com";
        String resetToken = "abc123";

        emailService.sendPasswordResetEmail(to, resetToken);

        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendPromotionalEmail() {
        String to = "promo@example.com";
        String promotionDetails = "50% OFF on all items";

        emailService.sendPromotionalEmail(to, promotionDetails);

        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }
}

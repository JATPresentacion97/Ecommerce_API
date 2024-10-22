package com.ecommerce.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("brilliancescented@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    public void sendOrderConfirmationEmail(String to, String orderDetails) {
        String subject = "Order Confirmation";
        String text = "Thank you for your order!\n\n" + orderDetails;
        sendSimpleMessage(to, subject, text);
    }

    public void sendPasswordResetEmail(String to, String resetToken) {
        String subject = "Password Reset Request";
        String text = "To reset your password, please click the following link: \n"
                + "http://example.com/reset-password?token=" + resetToken;
        sendSimpleMessage(to, subject, text);
    }

    public void sendPromotionalEmail(String to, String promotionDetails) {
        String subject = "Special Offer Just for You!";
        String text = "Check out our latest promotion:\n\n" + promotionDetails;
        sendSimpleMessage(to, subject, text);
    }
}

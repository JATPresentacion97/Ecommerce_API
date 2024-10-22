package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-order-confirmation")
    public ResponseEntity<Void> sendOrderConfirmationEmail(@RequestParam String to, @RequestParam String orderDetails) {
        emailService.sendOrderConfirmationEmail(to, orderDetails);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send-password-reset")
    public ResponseEntity<Void> sendPasswordResetEmail(@RequestParam String to, @RequestParam String resetToken) {
        emailService.sendPasswordResetEmail(to, resetToken);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send-promotional")
    public ResponseEntity<Void> sendPromotionalEmail(@RequestParam String to, @RequestParam String promotionDetails) {
        emailService.sendPromotionalEmail(to, promotionDetails);
        return ResponseEntity.ok().build();
    }
}

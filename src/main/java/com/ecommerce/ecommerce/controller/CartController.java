package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.Cart;
import com.ecommerce.ecommerce.model.UserLogin;
import com.ecommerce.ecommerce.service.CartService;
import com.ecommerce.ecommerce.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserLoginService userLoginService;

    /*
     * Get cart for the current user
     */
    @GetMapping("/{username}")
    public ResponseEntity<Cart> getCart(@PathVariable String username) {
        Optional<UserLogin> user = userLoginService.getUserLoginByUsername(username);

        if (user.isPresent()) {
            Optional<Cart> cart = cartService.getCartByUser(user.get());
            return cart.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /*
     * Add product to cart
     */
    @PostMapping("/{username}/add")
    public ResponseEntity<Cart> addProductToCart(
            @PathVariable String username, @RequestParam Long productId, @RequestParam int quantity) {

        Optional<UserLogin> user = userLoginService.getUserLoginByUsername(username);
        if (user.isPresent()) {
            Cart updatedCart = cartService.addProductToCart(user.get(), productId, quantity);
            return ResponseEntity.ok(updatedCart);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /*
     * Remove product from cart
     */
    @DeleteMapping("/{username}/remove")
    public ResponseEntity<Cart> removeProductFromCart(
            @PathVariable String username, @RequestParam Long productId) {

        Optional<UserLogin> user = userLoginService.getUserLoginByUsername(username);
        if (user.isPresent()) {
            Cart updatedCart = cartService.removeProductFromCart(user.get(), productId);
            return ResponseEntity.ok(updatedCart);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /*
     * Clear the cart for the user
     */
    @PostMapping("/{username}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable String username) {
        Optional<UserLogin> user = userLoginService.getUserLoginByUsername(username);

        if (user.isPresent()) {
            cartService.clearCart(user.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}

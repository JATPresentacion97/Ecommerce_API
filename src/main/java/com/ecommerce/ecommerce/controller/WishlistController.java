package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.model.UserLogin;
import com.ecommerce.ecommerce.model.Wishlist;
import com.ecommerce.ecommerce.service.ProductService;
import com.ecommerce.ecommerce.service.UserLoginService;
import com.ecommerce.ecommerce.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{username}/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserLoginService userLoginService;

    /*
     * Get all wishlist items for a user
     */
    @GetMapping
    public ResponseEntity<List<Wishlist>> getWishlist(@PathVariable String username) {
        UserLogin user = userLoginService.getUserLoginByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Wishlist> wishlistItems = wishlistService.getWishlistByUser(user);
        return ResponseEntity.ok(wishlistItems);
    }

    /*
     * Add a product to the wishlist
     */
    @PostMapping
    public ResponseEntity<Wishlist> addToWishlist(@PathVariable String username, @RequestParam Long productId) {
        UserLogin user = userLoginService.getUserLoginByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Wishlist wishlistItem = wishlistService.addToWishlist(user, product);
        return new ResponseEntity<>(wishlistItem, HttpStatus.CREATED);
    }

    /*
     * Remove a product from the wishlist
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeFromWishlist(@PathVariable String username, @PathVariable Long productId) {
        UserLogin user = userLoginService.getUserLoginByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        wishlistService.removeFromWishlist(user, product);
        return ResponseEntity.noContent().build();
    }
}

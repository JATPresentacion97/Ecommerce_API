package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.model.UserLogin;
import com.ecommerce.ecommerce.model.Wishlist;
import com.ecommerce.ecommerce.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    /*
     * Get all wishlist items for a user
     */
    public List<Wishlist> getWishlistByUser(UserLogin user) {
        return wishlistRepository.findByUser(user);
    }

    /*
     * Add a product to the wishlist
     */
    public Wishlist addToWishlist(UserLogin user, Product product) {
        Wishlist existingWishlistItem = wishlistRepository.findByUserAndProduct(user, product);
        if (existingWishlistItem == null) {
            Wishlist newWishlistItem = new Wishlist(user, product);
            return wishlistRepository.save(newWishlistItem);
        }
        return existingWishlistItem; // Product is already in the wishlist
    }

    /*
     * Remove a product from the wishlist
     */
    public void removeFromWishlist(UserLogin user, Product product) {
        Wishlist wishlistItem = wishlistRepository.findByUserAndProduct(user, product);
        if (wishlistItem != null) {
            wishlistRepository.delete(wishlistItem);
        }
    }
}

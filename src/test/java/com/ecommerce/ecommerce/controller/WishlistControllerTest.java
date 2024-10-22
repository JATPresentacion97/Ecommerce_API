package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.model.UserLogin;
import com.ecommerce.ecommerce.model.Wishlist;
import com.ecommerce.ecommerce.service.ProductService;
import com.ecommerce.ecommerce.service.UserLoginService;
import com.ecommerce.ecommerce.service.WishlistService;
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

public class WishlistControllerTest {

    @InjectMocks
    private WishlistController wishlistController;

    @Mock
    private WishlistService wishlistService;

    @Mock
    private ProductService productService;

    @Mock
    private UserLoginService userLoginService;

    private UserLogin user;
    private Product product;
    private Wishlist wishlistItem;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserLogin();
        user.setUsername("testuser");

        product = new Product(); // Assuming a no-arg constructor is available
        product.setId(1L);

        wishlistItem = new Wishlist(); // Assuming a no-arg constructor is available
        wishlistItem.setId(1L);
        wishlistItem.setProduct(product);
        wishlistItem.setUser(user);
    }

    @Test
    public void testGetWishlist() {
        List<Wishlist> wishlistItems = new ArrayList<>();
        wishlistItems.add(wishlistItem);

        when(userLoginService.getUserLoginByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(wishlistService.getWishlistByUser(user)).thenReturn(wishlistItems);

        ResponseEntity<List<Wishlist>> response = wishlistController.getWishlist(user.getUsername());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(wishlistItems, response.getBody());
    }

    @Test
    public void testAddToWishlist() {
        when(userLoginService.getUserLoginByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(productService.getProductById(product.getId())).thenReturn(Optional.of(product));
        when(wishlistService.addToWishlist(any(UserLogin.class), any(Product.class))).thenReturn(wishlistItem);

        ResponseEntity<Wishlist> response = wishlistController.addToWishlist(user.getUsername(), product.getId());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(wishlistItem, response.getBody());
    }

    @Test
    public void testRemoveFromWishlist() {
        when(userLoginService.getUserLoginByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(productService.getProductById(product.getId())).thenReturn(Optional.of(product));

        // We do not need to return anything from the remove method
        doNothing().when(wishlistService).removeFromWishlist(user, product);

        ResponseEntity<Void> response = wishlistController.removeFromWishlist(user.getUsername(), product.getId());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

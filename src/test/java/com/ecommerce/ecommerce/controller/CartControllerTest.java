package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.Cart;
import com.ecommerce.ecommerce.model.UserLogin;
import com.ecommerce.ecommerce.service.CartService;
import com.ecommerce.ecommerce.service.UserLoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartService cartService;

    @Mock
    private UserLoginService userLoginService;

    private UserLogin user;
    private Cart cart;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserLogin();
        user.setUsername("testuser");
        cart = new Cart();
    }

    @Test
    public void testGetCart_UserExists() {
        when(userLoginService.getUserLoginByUsername("testuser")).thenReturn(Optional.of(user));
        when(cartService.getCartByUser(user)).thenReturn(Optional.of(cart));

        ResponseEntity<Cart> response = cartController.getCart("testuser");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void testGetCart_UserDoesNotExist() {
        when(userLoginService.getUserLoginByUsername("nonexistent")).thenReturn(Optional.empty());

        ResponseEntity<Cart> response = cartController.getCart("nonexistent");
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    // Additional tests for add, remove, and clear cart
}

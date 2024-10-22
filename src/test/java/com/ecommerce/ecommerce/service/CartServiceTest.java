package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Cart;
import com.ecommerce.ecommerce.model.CartItem;
import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.model.UserLogin;
import com.ecommerce.ecommerce.repository.CartRepository;
import com.ecommerce.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    private UserLogin user;
    private Product product;
    private Cart cart;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserLogin();
        user.setUsername("testuser");

        product = new Product(); // Assuming a no-arg constructor is available
        product.setId(1L);
        product.setPrice(10.0);

        cart = new Cart(); // Assuming a no-arg constructor is available
        cart.setUser(user);
        cart.setItems(new ArrayList<>());
    }

    @Test
    public void testGetCartByUser() {
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));

        Optional<Cart> result = cartService.getCartByUser(user);
        assertEquals(cart, result.orElse(null));
    }

    @Test
    public void testAddProductToCart() {
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart updatedCart = cartService.addProductToCart(user, product.getId(), 2);

        assertEquals(1, updatedCart.getItems().size());
        assertEquals(20.0, updatedCart.getTotalPrice()); // 10.0 price * 2 quantity
    }

    @Test
    public void testRemoveProductFromCart() {
        CartItem cartItem = new CartItem(product, 2);
        cart.getItems().add(cartItem);
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart updatedCart = cartService.removeProductFromCart(user, product.getId());

        assertEquals(0, updatedCart.getItems().size());
        assertEquals(0.0, updatedCart.getTotalPrice());
    }

    @Test
    public void testClearCart() {
        CartItem cartItem = new CartItem(product, 2);
        cart.getItems().add(cartItem);
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        cartService.clearCart(user);

        assertEquals(0, cart.getItems().size());
        assertEquals(0.0, cart.getTotalPrice());
    }
}

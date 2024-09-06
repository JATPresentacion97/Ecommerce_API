package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Cart;
import com.ecommerce.ecommerce.model.CartItem;
import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.model.UserLogin;
import com.ecommerce.ecommerce.repository.CartRepository;
import com.ecommerce.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    /*
     * Get the cart for a specific user
     */
    public Optional<Cart> getCartByUser(UserLogin user) {
        return cartRepository.findByUser(user);
    }

    /*
     * Add product to cart
     */
    public Cart addProductToCart(UserLogin user, Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Cart cart = cartRepository.findByUser(user).orElse(new Cart());
        CartItem cartItem = new CartItem(product, quantity);

        cart.getItems().add(cartItem);
        updateCartTotal(cart);
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    /*
     * Remove product from cart
     */
    public Cart removeProductFromCart(UserLogin user, Long productId) {
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for user"));

        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        updateCartTotal(cart);
        return cartRepository.save(cart);
    }

    /*
     * Update cart total price
     */
    private void updateCartTotal(Cart cart) {
        double total = cart.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        cart.setTotalPrice(total);
    }

    /*
     * Clear cart
     */
    public void clearCart(UserLogin user) {
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for user"));

        cart.getItems().clear();
        cart.setTotalPrice(0);
        cartRepository.save(cart);
    }
}

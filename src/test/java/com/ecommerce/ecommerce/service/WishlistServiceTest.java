package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.model.UserLogin;
import com.ecommerce.ecommerce.model.Wishlist;
import com.ecommerce.ecommerce.repository.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WishlistServiceTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private WishlistService wishlistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWishlistByUser() {
        UserLogin user = new UserLogin();
        user.setUsername("testUser");
        Wishlist wishlistItem = new Wishlist(user, new Product());

        when(wishlistRepository.findByUser(user)).thenReturn(List.of(wishlistItem));

        List<Wishlist> result = wishlistService.getWishlistByUser(user);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(user, result.get(0).getUser());
        verify(wishlistRepository, times(1)).findByUser(user);
    }

    @Test
    void testAddToWishlist_NewItem() {
        UserLogin user = new UserLogin();
        user.setUsername("testUser");
        Product product = new Product();
        product.setName("Test Product");

        when(wishlistRepository.findByUserAndProduct(user, product)).thenReturn(null);
        when(wishlistRepository.save(any(Wishlist.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Wishlist result = wishlistService.addToWishlist(user, product);

        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals(product, result.getProduct());
        verify(wishlistRepository, times(1)).findByUserAndProduct(user, product);
        verify(wishlistRepository, times(1)).save(any(Wishlist.class));
    }

    @Test
    void testAddToWishlist_ExistingItem() {
        UserLogin user = new UserLogin();
        user.setUsername("testUser");
        Product product = new Product();
        product.setName("Test Product");

        Wishlist existingWishlist = new Wishlist(user, product);

        when(wishlistRepository.findByUserAndProduct(user, product)).thenReturn(existingWishlist);

        Wishlist result = wishlistService.addToWishlist(user, product);

        assertNotNull(result);
        assertEquals(existingWishlist, result);
        verify(wishlistRepository, times(1)).findByUserAndProduct(user, product);
        verify(wishlistRepository, times(0)).save(any(Wishlist.class));
    }

    @Test
    void testRemoveFromWishlist_ItemExists() {
        UserLogin user = new UserLogin();
        user.setUsername("testUser");
        Product product = new Product();
        product.setName("Test Product");

        Wishlist wishlistItem = new Wishlist(user, product);

        when(wishlistRepository.findByUserAndProduct(user, product)).thenReturn(wishlistItem);
        doNothing().when(wishlistRepository).delete(wishlistItem);

        wishlistService.removeFromWishlist(user, product);

        verify(wishlistRepository, times(1)).findByUserAndProduct(user, product);
        verify(wishlistRepository, times(1)).delete(wishlistItem);
    }

    @Test
    void testRemoveFromWishlist_ItemDoesNotExist() {
        UserLogin user = new UserLogin();
        user.setUsername("testUser");
        Product product = new Product();
        product.setName("Test Product");

        when(wishlistRepository.findByUserAndProduct(user, product)).thenReturn(null);

        wishlistService.removeFromWishlist(user, product);

        verify(wishlistRepository, times(1)).findByUserAndProduct(user, product);
        verify(wishlistRepository, times(0)).delete(any(Wishlist.class));
    }
}

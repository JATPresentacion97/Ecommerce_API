package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.model.UserLogin;
import com.ecommerce.ecommerce.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    // Find all wishlist items for a specific user
    List<Wishlist> findByUser(UserLogin user);

    // Find a specific wishlist item by user and product
    Wishlist findByUserAndProduct(UserLogin user, Product product);
}

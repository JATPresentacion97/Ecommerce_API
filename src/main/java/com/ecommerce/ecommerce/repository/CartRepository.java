package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.Cart;
import com.ecommerce.ecommerce.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(UserLogin user);

    // Additional methods like clearing cart, etc., can be added here
}

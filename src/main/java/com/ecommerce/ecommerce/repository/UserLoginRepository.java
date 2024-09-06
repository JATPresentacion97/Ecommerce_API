package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {

    // Find user by username (since it's unique)
    Optional<UserLogin> findByUsername(String username);

    // Check if a user exists by username
    boolean existsByUsername(String username);
}

package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.UserProfile;
import com.ecommerce.ecommerce.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    // Find user profile by the associated UserLogin
    Optional<UserProfile> findByUser(UserLogin user);
}

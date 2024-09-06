package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.UserLogin;
import com.ecommerce.ecommerce.repository.UserLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserLoginService {

    @Autowired
    private UserLoginRepository userLoginRepository;

    /*
     * Authenticate a user by checking if the username and password match.
     * In a real-world application, ensure that the password is hashed and salted.
     */
    public boolean authenticateUser(String username, String password) {
        Optional<UserLogin> userLogin = userLoginRepository.findByUsername(username);

        // If user exists and the password matches, return true, otherwise false
        return userLogin.isPresent() && userLogin.get().getPassword().equals(password);
    }

    /*
     * Register a new user. This could include validation logic to check if
     * the username is unique, the password is strong enough, etc.
     */
    public UserLogin registerUser(UserLogin userLogin) {
        // Check if the username already exists
        if (userLoginRepository.existsByUsername(userLogin.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // In a real-world app, you would hash and salt the password before saving
        // Save the user to the repository
        return userLoginRepository.save(userLogin);
    }

    /*
     * Find a user by their username
     */
    public Optional<UserLogin> getUserLoginByUsername(String username) {
        return userLoginRepository.findByUsername(username);
    }

    /*
     * Logout logic can be handled by setting the user session inactive or
     * performing any other business logic as needed.
     */
    public void logoutUser(String username) {
        Optional<UserLogin> userLogin = userLoginRepository.findByUsername(username);

        if (userLogin.isPresent()) {
            UserLogin user = userLogin.get();
            user.setActive(false);  // Mark user as inactive or handle any logout logic
            userLoginRepository.save(user);
        }
    }

    /*
     * Update a user's information (like password, email, etc.).
     */
    public UserLogin updateUser(Long userId, UserLogin updatedUser) {
        Optional<UserLogin> existingUser = userLoginRepository.findById(userId);

        if (existingUser.isPresent()) {
            UserLogin user = existingUser.get();
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword()); // In real apps, hash it!
            user.setActive(updatedUser.isActive());

            return userLoginRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }
}

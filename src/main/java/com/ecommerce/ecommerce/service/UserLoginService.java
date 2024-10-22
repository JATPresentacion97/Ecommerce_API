package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.UserLogin;
import com.ecommerce.ecommerce.repository.UserLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserLoginService {

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /*
     * Authenticate a user by checking if the username and password match.
     * Uses hashed password for secure authentication.
     */
    public boolean authenticateUser(String username, String password) {
        Optional<UserLogin> userLogin = userLoginRepository.findByUsername(username);

        // If user exists and the password matches, return true, otherwise false
        return userLogin.isPresent() && passwordEncoder.matches(password, userLogin.get().getPassword());
    }

    /*
     * Register a new user. Checks if the username is unique before saving.
     */
    public UserLogin registerUser(UserLogin userLogin) {
        // Check if the username already exists
        if (userLoginRepository.existsByUsername(userLogin.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Hash and salt the password before saving
        userLogin.setPassword(passwordEncoder.encode(userLogin.getPassword()));
        return userLoginRepository.save(userLogin);
    }

    /*
     * Find a user by their username.
     */
    public Optional<UserLogin> getUserLoginByUsername(String username) {
        return userLoginRepository.findByUsername(username);
    }

    /*
     * Logout logic can be handled by marking the user inactive or
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
     * The password should be hashed before saving.
     */
    public UserLogin updateUser(Long userId, UserLogin updatedUser) {
        Optional<UserLogin> existingUser = userLoginRepository.findById(userId);

        if (existingUser.isPresent()) {
            UserLogin user = existingUser.get();
            user.setEmail(updatedUser.getEmail());
            user.setActive(updatedUser.isActive());

            // Hash the new password if it has been changed
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }

            return userLoginRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }
}

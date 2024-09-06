package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.UserLogin;
import com.ecommerce.ecommerce.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user/login")
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    /*
     * Handle user login
     * */
    @PostMapping
    public ResponseEntity<String> loginUser(@RequestBody UserLogin userLogin) {
        boolean isAuthenticated = userLoginService.authenticateUser(userLogin.getUsername(), userLogin.getPassword());

        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    /*
     * Get user login details by username
     * */
    @GetMapping("/{username}")
    public ResponseEntity<UserLogin> getUserLoginByUsername(@PathVariable String username) {
        Optional<UserLogin> userLogin = userLoginService.getUserLoginByUsername(username);
        return userLogin.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*
     * Logout user
     * */
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@RequestParam String username) {
        userLoginService.logoutUser(username);
        return ResponseEntity.ok("User logged out successfully");
    }
}

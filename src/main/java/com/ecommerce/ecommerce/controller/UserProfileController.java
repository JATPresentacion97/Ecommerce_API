package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.UserLogin;
import com.ecommerce.ecommerce.model.UserProfile;
import com.ecommerce.ecommerce.service.UserLoginService;
import com.ecommerce.ecommerce.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user/profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserLoginService userLoginService;

    /*
     * Get the user profile by username
     */
    @GetMapping("/{username}")
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable String username) {
        Optional<UserLogin> user = userLoginService.getUserLoginByUsername(username);

        if (user.isPresent()) {
            Optional<UserProfile> profile = userProfileService.getUserProfileByUser(user.get());
            return profile.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /*
     * Create or update user profile
     */
    @PostMapping("/{username}")
    public ResponseEntity<UserProfile> createOrUpdateProfile(
            @PathVariable String username, @RequestBody UserProfile profileData) {

        Optional<UserLogin> user = userLoginService.getUserLoginByUsername(username);

        if (user.isPresent()) {
            UserProfile updatedProfile = userProfileService.createOrUpdateProfile(user.get(), profileData);
            return ResponseEntity.ok(updatedProfile);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /*
     * Delete user profile
     */
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUserProfile(@PathVariable String username) {
        Optional<UserLogin> user = userLoginService.getUserLoginByUsername(username);

        if (user.isPresent()) {
            userProfileService.deleteUserProfile(user.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}

package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.UserLogin;
import com.ecommerce.ecommerce.model.UserProfile;
import com.ecommerce.ecommerce.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    /*
     * Get a user's profile by the associated UserLogin
     */
    public Optional<UserProfile> getUserProfileByUser(UserLogin user) {
        return userProfileRepository.findByUser(user);
    }

    /*
     * Create or update the user profile
     */
    public UserProfile createOrUpdateProfile(UserLogin user, UserProfile profileData) {
        Optional<UserProfile> existingProfile = userProfileRepository.findByUser(user);

        if (existingProfile.isPresent()) {
            UserProfile profile = existingProfile.get();
            profile.setFirstName(profileData.getFirstName());
            profile.setLastName(profileData.getLastName());
            profile.setAddress(profileData.getAddress());
            profile.setPhoneNumber(profileData.getPhoneNumber());
            return userProfileRepository.save(profile);
        } else {
            // Create new profile if it doesn't exist
            profileData.setUser(user);
            return userProfileRepository.save(profileData);
        }
    }

    /*
     * Delete a user's profile
     */
    public void deleteUserProfile(UserLogin user) {
        Optional<UserProfile> existingProfile = userProfileRepository.findByUser(user);
        existingProfile.ifPresent(userProfileRepository::delete);
    }
}

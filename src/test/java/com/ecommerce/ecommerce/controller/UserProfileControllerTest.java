package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.UserProfile;
import com.ecommerce.ecommerce.model.UserLogin;
import com.ecommerce.ecommerce.service.UserProfileService;
import com.ecommerce.ecommerce.service.UserLoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserProfileControllerTest {

    @InjectMocks
    private UserProfileController userProfileController;

    @Mock
    private UserProfileService userProfileService;

    @Mock
    private UserLoginService userLoginService;

    private UserLogin user;
    private UserProfile profile;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserLogin();
        user.setUsername("testuser");
        profile = new UserProfile();
        profile.setFirstName("Test");
    }

    @Test
    public void testGetUserProfile() {
        when(userLoginService.getUserLoginByUsername("testuser")).thenReturn(Optional.of(user));
        when(userProfileService.getUserProfileByUser(user)).thenReturn(Optional.of(profile));

        ResponseEntity<UserProfile> response = userProfileController.getUserProfile("testuser");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(profile, response.getBody());
    }

    @Test
    public void testCreateOrUpdateProfile() {
        when(userLoginService.getUserLoginByUsername("testuser")).thenReturn(Optional.of(user));
        when(userProfileService.createOrUpdateProfile(any(UserLogin.class), any(UserProfile.class))).thenReturn(profile);

        ResponseEntity<UserProfile> response = userProfileController.createOrUpdateProfile("testuser", profile);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(profile, response.getBody());
    }

    // Additional tests for delete profile
}

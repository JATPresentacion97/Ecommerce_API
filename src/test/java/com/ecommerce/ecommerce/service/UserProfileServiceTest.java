package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.UserLogin;
import com.ecommerce.ecommerce.model.UserProfile;
import com.ecommerce.ecommerce.repository.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserProfileServiceTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private UserProfileService userProfileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserProfileByUser() {
        UserLogin user = new UserLogin();
        user.setUsername("testUser");
        UserProfile profile = new UserProfile();
        profile.setUser(user);

        when(userProfileRepository.findByUser(user)).thenReturn(Optional.of(profile));

        Optional<UserProfile> result = userProfileService.getUserProfileByUser(user);

        assertTrue(result.isPresent());
        assertEquals(user, result.get().getUser());
        verify(userProfileRepository, times(1)).findByUser(user);
    }

    @Test
    void testCreateOrUpdateProfile_CreateNewProfile() {
        UserLogin user = new UserLogin();
        user.setUsername("testUser");
        UserProfile newProfile = new UserProfile();
        newProfile.setFirstName("John");
        newProfile.setLastName("Doe");

        when(userProfileRepository.findByUser(user)).thenReturn(Optional.empty());
        when(userProfileRepository.save(newProfile)).thenReturn(newProfile);

        UserProfile result = userProfileService.createOrUpdateProfile(user, newProfile);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(userProfileRepository, times(1)).findByUser(user);
        verify(userProfileRepository, times(1)).save(newProfile);
    }

    @Test
    void testCreateOrUpdateProfile_UpdateExistingProfile() {
        UserLogin user = new UserLogin();
        user.setUsername("testUser");

        UserProfile existingProfile = new UserProfile();
        existingProfile.setFirstName("OldName");

        UserProfile updatedProfile = new UserProfile();
        updatedProfile.setFirstName("NewName");
        updatedProfile.setLastName("Doe");
        updatedProfile.setPhoneNumber("123456789");

        when(userProfileRepository.findByUser(user)).thenReturn(Optional.of(existingProfile));
        when(userProfileRepository.save(existingProfile)).thenReturn(existingProfile);

        UserProfile result = userProfileService.createOrUpdateProfile(user, updatedProfile);

        assertNotNull(result);
        assertEquals("NewName", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("123456789", result.getPhoneNumber());
        verify(userProfileRepository, times(1)).findByUser(user);
        verify(userProfileRepository, times(1)).save(existingProfile);
    }

    @Test
    void testDeleteUserProfile_ProfileExists() {
        UserLogin user = new UserLogin();
        user.setUsername("testUser");
        UserProfile profile = new UserProfile();
        profile.setUser(user);

        when(userProfileRepository.findByUser(user)).thenReturn(Optional.of(profile));
        doNothing().when(userProfileRepository).delete(profile);

        userProfileService.deleteUserProfile(user);

        verify(userProfileRepository, times(1)).findByUser(user);
        verify(userProfileRepository, times(1)).delete(profile);
    }

    @Test
    void testDeleteUserProfile_ProfileDoesNotExist() {
        UserLogin user = new UserLogin();
        user.setUsername("testUser");

        when(userProfileRepository.findByUser(user)).thenReturn(Optional.empty());

        userProfileService.deleteUserProfile(user);

        verify(userProfileRepository, times(1)).findByUser(user);
        verify(userProfileRepository, times(0)).delete(any(UserProfile.class));
    }
}

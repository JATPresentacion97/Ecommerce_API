package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.UserLogin;
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

public class UserLoginControllerTest {

    @InjectMocks
    private UserLoginController userLoginController;

    @Mock
    private UserLoginService userLoginService;

    private UserLogin user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserLogin();
        user.setUsername("testuser");
        user.setPassword("password");
    }

    @Test
    public void testLoginUser_Success() {
        // Mock the authentication method
        when(userLoginService.authenticateUser("testuser", "password")).thenReturn(true);

        // Prepare the request body
        UserLogin loginRequest = new UserLogin();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password");

        // Call the login method
        ResponseEntity<String> response = userLoginController.loginUser(loginRequest);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Login successful!", response.getBody());
    }

    @Test
    public void testLoginUser_Failure() {
        // Mock the authentication method to return false for invalid credentials
        when(userLoginService.authenticateUser("testuser", "wrongpassword")).thenReturn(false);

        // Prepare the request body
        UserLogin loginRequest = new UserLogin();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("wrongpassword");

        // Call the login method
        ResponseEntity<String> response = userLoginController.loginUser(loginRequest);

        // Verify the response
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials", response.getBody());
    }

    @Test
    public void testGetUserLoginByUsername_Success() {
        // Mock the user login retrieval method
        when(userLoginService.getUserLoginByUsername("testuser")).thenReturn(Optional.of(user));

        // Call the get user login method
        ResponseEntity<UserLogin> response = userLoginController.getUserLoginByUsername("testuser");

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testGetUserLoginByUsername_NotFound() {
        // Mock the user login retrieval method to return an empty Optional
        when(userLoginService.getUserLoginByUsername("testuser")).thenReturn(Optional.empty());

        // Call the get user login method
        ResponseEntity<UserLogin> response = userLoginController.getUserLoginByUsername("testuser");

        // Verify the response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testLogoutUser() {
        // Call the logout method
        ResponseEntity<String> response = userLoginController.logoutUser("testuser");

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User logged out successfully", response.getBody());

        // Verify that the logoutUser method in the service was called
        verify(userLoginService, times(1)).logoutUser("testuser");
    }
}

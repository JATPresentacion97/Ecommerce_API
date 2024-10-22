package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.UserLogin;
import com.ecommerce.ecommerce.repository.UserLoginRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserLoginServiceTest {

    @Mock
    private UserLoginRepository userLoginRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserLoginService userLoginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthenticateUser_Success() {
        String username = "testUser";
        String password = "password";
        UserLogin userLogin = new UserLogin();
        userLogin.setUsername(username);
        userLogin.setPassword("hashedPassword");

        when(userLoginRepository.findByUsername(username)).thenReturn(Optional.of(userLogin));
        when(passwordEncoder.matches(password, userLogin.getPassword())).thenReturn(true);

        boolean result = userLoginService.authenticateUser(username, password);

        assertTrue(result);
        verify(userLoginRepository, times(1)).findByUsername(username);
        verify(passwordEncoder, times(1)).matches(password, userLogin.getPassword());
    }

    @Test
    void testAuthenticateUser_Failure() {
        String username = "testUser";
        String password = "wrongPassword";

        when(userLoginRepository.findByUsername(username)).thenReturn(Optional.empty());

        boolean result = userLoginService.authenticateUser(username, password);

        assertFalse(result);
        verify(userLoginRepository, times(1)).findByUsername(username);
        verify(passwordEncoder, times(0)).matches(anyString(), anyString());
    }

    @Test
    void testRegisterUser_Success() {
        UserLogin newUser = new UserLogin();
        newUser.setUsername("newUser");
        newUser.setPassword("plainPassword");

        when(userLoginRepository.existsByUsername(newUser.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(newUser.getPassword())).thenReturn("hashedPassword");
        when(userLoginRepository.save(any(UserLogin.class))).thenReturn(newUser);

        UserLogin result = userLoginService.registerUser(newUser);

        assertNotNull(result);
        assertEquals("newUser", result.getUsername());
        verify(userLoginRepository, times(1)).existsByUsername(newUser.getUsername());
        verify(passwordEncoder, times(1)).encode(newUser.getPassword());
        verify(userLoginRepository, times(1)).save(any(UserLogin.class));
    }

    @Test
    void testRegisterUser_UsernameExists() {
        UserLogin newUser = new UserLogin();
        newUser.setUsername("existingUser");

        when(userLoginRepository.existsByUsername(newUser.getUsername())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> userLoginService.registerUser(newUser));

        verify(userLoginRepository, times(1)).existsByUsername(newUser.getUsername());
        verify(passwordEncoder, times(0)).encode(anyString());
        verify(userLoginRepository, times(0)).save(any(UserLogin.class));
    }

    @Test
    void testGetUserLoginByUsername() {
        String username = "testUser";
        UserLogin userLogin = new UserLogin();
        userLogin.setUsername(username);

        when(userLoginRepository.findByUsername(username)).thenReturn(Optional.of(userLogin));

        Optional<UserLogin> result = userLoginService.getUserLoginByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
        verify(userLoginRepository, times(1)).findByUsername(username);
    }

    @Test
    void testLogoutUser() {
        String username = "testUser";
        UserLogin userLogin = new UserLogin();
        userLogin.setUsername(username);
        userLogin.setActive(true);

        when(userLoginRepository.findByUsername(username)).thenReturn(Optional.of(userLogin));

        userLoginService.logoutUser(username);

        assertFalse(userLogin.isActive());  // The user should now be inactive
        verify(userLoginRepository, times(1)).findByUsername(username);
        verify(userLoginRepository, times(1)).save(userLogin);
    }

    @Test
    void testUpdateUser_Success() {
        Long userId = 1L;
        UserLogin existingUser = new UserLogin();
        existingUser.setId(userId);
        existingUser.setEmail("old@example.com");

        UserLogin updatedUser = new UserLogin();
        updatedUser.setEmail("new@example.com");
        updatedUser.setPassword("newPassword");

        when(userLoginRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode(updatedUser.getPassword())).thenReturn("hashedPassword");
        when(userLoginRepository.save(existingUser)).thenReturn(existingUser);

        UserLogin result = userLoginService.updateUser(userId, updatedUser);

        assertNotNull(result);
        assertEquals("new@example.com", result.getEmail());
        verify(userLoginRepository, times(1)).findById(userId);
        verify(passwordEncoder, times(1)).encode(updatedUser.getPassword());
        verify(userLoginRepository, times(1)).save(existingUser);
    }

    @Test
    void testUpdateUser_UserNotFound() {
        Long userId = 1L;
        UserLogin updatedUser = new UserLogin();
        updatedUser.setEmail("new@example.com");

        when(userLoginRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userLoginService.updateUser(userId, updatedUser));

        verify(userLoginRepository, times(1)).findById(userId);
        verify(userLoginRepository, times(0)).save(any(UserLogin.class));
    }
}

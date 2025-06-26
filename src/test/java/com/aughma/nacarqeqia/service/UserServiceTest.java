package com.aughma.nacarqeqia.service;

import com.aughma.nacarqeqia.entity.Authority;
import com.aughma.nacarqeqia.entity.User;
import com.aughma.nacarqeqia.repository.AuthorityRepository;
import com.aughma.nacarqeqia.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock // Creates a mock implementation for the UserRepository
    private UserRepository userRepository;

    @Mock // Creates a mock for the AuthorityRepository
    private AuthorityRepository authorityRepository;

    @Mock // Creates a mock for the PasswordEncoder
    private PasswordEncoder passwordEncoder;

    @InjectMocks // Creates an instance of UserService and injects the mocks into it
    private UserService userService;

    @Test
    void testRegister_Success() {
        // 1. Arrange (Set up the test)
        String username = "testuser";
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword123";

        // Define the behavior of the mocked password encoder
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        // 2. Act (Execute the method to be tested)
        userService.register(username, rawPassword);

        // 3. Assert & Verify (Check the results)

        // Use ArgumentCaptor to capture the User object that was passed to the save method
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture()); // Verify that save was called and capture the argument
        User savedUser = userArgumentCaptor.getValue();

        // Assert that the saved user has the correct properties
        assertEquals(username, savedUser.getUsername());
        assertEquals(encodedPassword, savedUser.getPassword());
        assertTrue(savedUser.isEnabled());

        // Use ArgumentCaptor to capture the Authority object
        ArgumentCaptor<Authority> authorityArgumentCaptor = ArgumentCaptor.forClass(Authority.class);
        verify(authorityRepository).save(authorityArgumentCaptor.capture()); // Verify and capture
        Authority savedAuthority = authorityArgumentCaptor.getValue();

        // Assert that the saved authority is correct
        assertEquals("ROLE_USER", savedAuthority.getAuthority());
        assertEquals(username, savedAuthority.getUser().getUsername()); // Check it's linked to the correct user

        // Verify that the encode method was called exactly once
        verify(passwordEncoder, times(1)).encode(rawPassword);
    }
}
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

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorityRepository authorityRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void testRegister_Success() {
        // 1. arrange
        String username = "testuser";
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword123";

        // define the behavior
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        // 2. act
        userService.register(username, rawPassword);

        // 3. assert & verify

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User savedUser = userArgumentCaptor.getValue();

        // user has the correct properties
        assertEquals(username, savedUser.getUsername());
        assertEquals(encodedPassword, savedUser.getPassword());
        assertTrue(savedUser.isEnabled());

        // Use argumentCaptor to capture the authority object
        ArgumentCaptor<Authority> authorityArgumentCaptor = ArgumentCaptor.forClass(Authority.class);
        verify(authorityRepository).save(authorityArgumentCaptor.capture()); // verify and capture
        Authority savedAuthority = authorityArgumentCaptor.getValue();

        // assert that the saved authority is correct
        assertEquals("ROLE_USER", savedAuthority.getAuthority());
        assertEquals(username, savedAuthority.getUser().getUsername()); // check it's linked to the correct user

        // verify that the encode method was called exactly once
        verify(passwordEncoder, times(1)).encode(rawPassword);
    }
}
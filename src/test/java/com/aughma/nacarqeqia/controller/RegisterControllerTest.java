package com.aughma.nacarqeqia.controller;

import com.aughma.nacarqeqia.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @Test
    void testProcessRegistration_Success() throws Exception {
        // Arrange
        String username = "newuser";
        String password = "password123";

        // Define mock behavior for our mocked UserService
        when(userService.existsUsername(username)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(post("/register")
                        .param("username", username)
                        .param("password", password)
                        .param("confirmPassword", password)
                        .with(csrf())) // Add CSRF token
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?success"))
                .andExpect(flash().attributeExists("successMessage"));
    }

    @Test
    void testProcessRegistration_UsernameExists() throws Exception {
        // Arrange
        String existingUsername = "admin";
        String password = "password123";

        // Define mock behavior for our mocked UserService
        when(userService.existsUsername(existingUsername)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(post("/register")
                        .param("username", existingUsername)
                        .param("password", password)
                        .param("confirmPassword", password)
                        .with(csrf())) // Add CSRF token
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("usernameError"));
    }
}
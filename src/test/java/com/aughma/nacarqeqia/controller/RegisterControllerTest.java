package com.aughma.nacarqeqia.controller;

import com.aughma.nacarqeqia.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegisterController.class) // Focus only on the RegisterController
class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc; // A bean for simulating HTTP requests

    @MockBean // Creates a mock of UserService and adds it to the application context
    private UserService userService;

    @Test
    void testProcessRegistration_Success() throws Exception {
        // Arrange
        String username = "newuser";
        String password = "password123";

        // Define mock behavior
        when(userService.existsUsername(username)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(post("/register")
                        .param("username", username)
                        .param("password", password)
                        .param("confirmPassword", password)
                        .with(csrf())) // Add CSRF token for the POST request
                .andExpect(status().is3xxRedirection()) // Expect a redirect (HTTP 302)
                .andExpect(redirectedUrl("/login?success")) // Expect redirection to the login page
                .andExpect(flash().attributeExists("successMessage")); // Expect a success message in flash attributes
    }

    @Test
    void testProcessRegistration_UsernameExists() throws Exception {
        // Arrange
        String existingUsername = "admin";
        String password = "password123";

        // Define mock behavior: pretend the username already exists
        when(userService.existsUsername(existingUsername)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(post("/register")
                        .param("username", existingUsername)
                        .param("password", password)
                        .param("confirmPassword", password)
                        .with(csrf()))
                .andExpect(status().isOk()) // Expect HTTP 200 OK (no redirect)
                .andExpect(view().name("register")) // Expect to stay on the register page
                .andExpect(model().attributeExists("usernameError")); // Expect a usernameError in the model
    }
}
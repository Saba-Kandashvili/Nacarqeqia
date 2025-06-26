package com.aughma.nacarqeqia.controller;

import com.aughma.nacarqeqia.model.RegisterForm;
import com.aughma.nacarqeqia.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private static final Logger log = LoggerFactory.getLogger(RegisterController.class);

    private final UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(@ModelAttribute("registerForm") RegisterForm registerForm) {
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(
            @Valid @ModelAttribute("registerForm") RegisterForm registerForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        // Check if passwords match
        if (!registerForm.getPassword().equals(registerForm.getConfirmPassword())) {
            log.warn("Registration failed for username '{}': Passwords do not match.", registerForm.getUsername());
            model.addAttribute("passwordMatchError", "Passwords do not match");
            return "register";
        }

        // Check if username already exists
        if (userService.existsUsername(registerForm.getUsername())) {
            log.warn("Registration failed: Username '{}' already exists.", registerForm.getUsername());
            model.addAttribute("usernameError", "Username already exists");
            return "register";
        }

        // If there are other validation errors
        if (bindingResult.hasErrors()) {
            return "register";
        }

        // Register the new user
        userService.register(registerForm.getUsername(), registerForm.getPassword());


        log.info("New user registered successfully: '{}'", registerForm.getUsername());

        // Redirect to login page with success message
        redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please log in.");
        return "redirect:/login?success";
    }
}
package com.aughma.nacarqeqia.controller;

import com.aughma.nacarqeqia.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor // Use constructor injection
public class ProfileController {

    private final OrderRepository orderRepository; // Inject the repository

    @GetMapping
    public String profile(Principal principal, Pageable pageable, Model model) {
        // Get the username of the currently logged-in user
        String username = principal.getName();

        // Create a page request
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), 9);

        // Use our new repository method to find the user's orders
        model.addAttribute("orderPage", orderRepository.findByAuthorUsername(username, pageRequest));

        // Add the username to the model in case we want to greet them
        model.addAttribute("username", username);

        return "profile";
    }
}
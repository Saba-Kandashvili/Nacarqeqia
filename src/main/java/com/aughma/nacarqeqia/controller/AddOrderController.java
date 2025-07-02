package com.aughma.nacarqeqia.controller;

import com.aughma.nacarqeqia.entity.Order;
import com.aughma.nacarqeqia.model.AddOrder;
import com.aughma.nacarqeqia.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ui.Model;

@Controller
public class AddOrderController {

    private static final Logger log = LoggerFactory.getLogger(AddOrderController.class);

    @Autowired
    private OrderService orderService;

    @GetMapping("/order/add")
    public String addOrder(Model model) {
        // Explicitly add a new, empty AddOrder object to the model.
        // This guarantees the template will have what it needs.
        model.addAttribute("addOrder", new AddOrder());
        return "order/add";
    }

    @PostMapping("/order/add")
    public String submitOrder(
            @Valid @ModelAttribute("addOrder") AddOrder addOrder,
            BindingResult bindingResult,
            // The @RequestParam for the image is no longer needed here
            Principal principal,
            RedirectAttributes redirectAttributes
    ) {
        // Get the image from the model object now
        MultipartFile image = addOrder.getImage();

        // Check if no file was selected
        if (image == null || image.isEmpty()) {
            // This will now work correctly because the 'image' field exists in AddOrder
            bindingResult.rejectValue("image", "image.empty", "Please select an image file.");
        }

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            log.warn("Order submission failed validation for user '{}'. Errors: {}", principal.getName(), bindingResult.getAllErrors());
            return "order/add"; // Return to the form page
        }

        // Get signed-in username
        String username = principal.getName();
        log.info("User '{}' is submitting a new order for deceased '{}'.", username, addOrder.getDeceasedName());

        try {
            // Save and redirect, passing the image from the model
            Order newOrder = orderService.save(username, addOrder, addOrder.getImage());
            redirectAttributes.addFlashAttribute("successMessage", "Order added successfully!");
            log.info("Order with ID {} created successfully for user '{}'.", newOrder.getId(), username);
            return "redirect:/order?id=" + newOrder.getId();
        } catch (Exception e) {
            log.error("Failed to save image for order submitted by user '{}'.", username, e);
            // This is a general error, not tied to a specific field on the form
            bindingResult.reject("image.save.error", "There was an error saving the image. Please try again.");
            return "order/add";
        }
    }
}
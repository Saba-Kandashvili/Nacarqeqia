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
            @RequestParam("image") MultipartFile image,
            Principal principal,
            RedirectAttributes redirectAttributes
    ) { // Remove 'throws IOException' from here
        // if no file selected
        if (image.isEmpty()) {
            bindingResult.rejectValue("image", "image.empty", "Please select an image file.");
        }

        // validation errors?
        if (bindingResult.hasErrors()) {
            log.warn("Order submission failed validation for user '{}'. Errors: {}", principal.getName(), bindingResult.getAllErrors());
            return "order/add";
        }

        // get signed-in username
        String username = principal.getName();
        log.info("User '{}' is submitting a new order for deceased '{}'.", username, addOrder.getDeceasedName());

        try {
            // save and redirect
            Order newOrder = orderService.save(username, addOrder, image);
            redirectAttributes.addFlashAttribute("successMessage", "Order added successfully!");
            log.info("Order with ID {} created successfully for user '{}'.", newOrder.getId(), username);
            return "redirect:/order?id=" + newOrder.getId();
        } catch (Exception e) {
            log.error("Failed to save image for order submitted by user '{}'.", username, e);
            bindingResult.reject("image.save.error", "There was an error saving the image. Please try again.");
            return "order/add";
        }
    }
}
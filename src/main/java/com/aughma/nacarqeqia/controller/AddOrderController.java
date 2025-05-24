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

@Controller
public class AddOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order/add")
    public String addOrder(@ModelAttribute("addOrder") AddOrder addOrder) {
        // The blank AddOrder object will be added to the model automatically
        return "order/add";
    }

    @PostMapping("/order/add")
    public String submitOrder(
            @Valid @ModelAttribute("addOrder") AddOrder addOrder,
            BindingResult bindingResult,
            @RequestParam("image") MultipartFile image,
            Principal principal,                                // ← inject current user
            RedirectAttributes redirectAttributes
    ) throws IOException {
        // if no file selected
        if (image.isEmpty()) {
            bindingResult.rejectValue("image", "image.empty", "Please select an image file.");
        }

        // validation errors?
        if (bindingResult.hasErrors()) {
            return "order/add";
        }

        // get signed-in username
        String username = principal.getName();

        // save and redirect
        Order newOrder = orderService.save(username, addOrder, image);
        redirectAttributes.addFlashAttribute("successMessage", "Order added successfully!");
        return "redirect:/order?id=" + newOrder.getId();
    }
}

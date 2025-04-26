package com.aughma.nacarqeqia.controller;

import com.aughma.nacarqeqia.entity.Order;
import com.aughma.nacarqeqia.model.AddOrder;
import com.aughma.nacarqeqia.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class AddOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order/add")
    public String addOrder(Model model) {
        model.addAttribute("addOrder", new AddOrder());
        return "order/add";
    }

    @PostMapping("/order/add")
    public String productAdd(
            @Valid @ModelAttribute("addOrder") AddOrder addOrder,
            BindingResult bindingResult,
            @RequestParam("image") MultipartFile image,
            Model model,
            RedirectAttributes redirectAttributes
    ) throws IOException {

        if (image.isEmpty()) {
            model.addAttribute("imageError", "Please select an image file.");
        }

        if (bindingResult.hasErrors() || image.isEmpty()) {
            model.addAttribute("addOrder", addOrder);
            return "order/add";
        }

        Order newOrder = orderService.save(addOrder, image);

        redirectAttributes.addFlashAttribute("successMessage", "Order added successfully");
        return "redirect:/order?id=" + newOrder.getId();
    }
}

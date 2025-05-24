package com.aughma.nacarqeqia.controller;

import com.aughma.nacarqeqia.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository repository;

    @GetMapping("/orders")
    public String orders(Pageable pageable, Model model) {
        PageRequest pageRequest = PageRequest.of(
                pageable.getPageNumber(),
                50  // Show 50 items per page
        );
        model.addAttribute("order", repository.findAll(pageRequest));
        return "order/all";
    }


    @GetMapping("/order")
    public String order(@RequestParam Long id, Model model, RedirectAttributes redirectAttributes) {
        return repository.findById(id).map(order -> {
            model.addAttribute("order", order);
            return "order/single";
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("errorMessage", "Order not found.");
            return "redirect:/orders";
        });
    }
}

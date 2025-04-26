package com.aughma.nacarqeqia.controller;


import com.aughma.nacarqeqia.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository repository;

    @GetMapping("/orders")
    public String orders(Pageable pageable, Model model) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        model.addAttribute("order", repository.findAll(pageRequest));
        return "order/all";
    }

    @GetMapping("/order")
    public String order(@RequestParam Long id, Model model) {

        model.addAttribute("order", repository.findById(id).orElse(null));
        return "order/single";
    }
}

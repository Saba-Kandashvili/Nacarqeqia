package com.aughma.nacarqeqia.controller;

import com.aughma.nacarqeqia.repository.ContactUsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin") // All routes in this controller will start with /admin
@RequiredArgsConstructor
public class AdminController {

    private final ContactUsRepository contactUsRepository;

    @GetMapping("/messages")
    public String viewMessages(Pageable pageable, Model model) {
        // Create a page request, sorting by the newest messages first
        PageRequest pageRequest = PageRequest.of(
                pageable.getPageNumber(),
                10, // Show 10 messages per page
                Sort.by("submittedAt").descending()
        );

        // Fetch the paginated messages from the repository
        model.addAttribute("messagePage", contactUsRepository.findAll(pageRequest));

        return "admin/messages"; // Maps to templates/admin/messages.html
    }
}
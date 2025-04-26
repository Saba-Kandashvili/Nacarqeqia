package com.aughma.nacarqeqia.controller;

import com.aughma.nacarqeqia.model.ContactUs;
import com.aughma.nacarqeqia.service.ContactUsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactUsController {

    @Autowired
    private ContactUsService contactUsService;    // ← inject your service

    @GetMapping("/contact")
    public String contactUs(Model model) {
        model.addAttribute("contactUs", new ContactUs());
        return "contact-us";
    }

    @PostMapping("/contact")
    public String contactUsSubmit(
            @Valid @ModelAttribute("contactUs") ContactUs dto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "contact-us";
        }

        // ← save it to the database
        contactUsService.save(dto);

        redirectAttributes.addFlashAttribute("successMessage", "Message sent successfully");
        return "redirect:/contact";
    }
}

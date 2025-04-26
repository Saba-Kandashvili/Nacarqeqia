package com.aughma.nacarqeqia.controller;

import com.aughma.nacarqeqia.model.ContactUs;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AboutUsController {

    @GetMapping("/about")
    public String abputUs() {
        return "about-us";
    }

}

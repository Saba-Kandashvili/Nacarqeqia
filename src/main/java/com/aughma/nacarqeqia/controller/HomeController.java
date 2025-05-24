package com.aughma.nacarqeqia.controller;

import com.aughma.nacarqeqia.util.MessageHelper;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller
public class HomeController {

    private final MessageHelper messageHelper;

    public HomeController(MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }

    /**
     * Test endpoint to verify messages are loading correctly
     */
    @GetMapping("/test-messages")
    @ResponseBody
    public Map<String, Object> testMessages() {
        Map<String, Object> result = new HashMap<>();
        
        // Get current locale
        Locale currentLocale = LocaleContextHolder.getLocale();
        result.put("currentLocale", currentLocale.toString());
        
        // Test some messages in English
        Map<String, String> enMessages = new HashMap<>();
        enMessages.put("app.title", messageHelper.getMessage("app.title", Locale.ENGLISH));
        enMessages.put("app.slogan", messageHelper.getMessage("app.slogan", Locale.ENGLISH));
        enMessages.put("header.contact", messageHelper.getMessage("header.contact", Locale.ENGLISH));
        result.put("englishMessages", enMessages);
        
        // Test some messages in Georgian
        Map<String, String> kaMessages = new HashMap<>();
        Locale georgianLocale = Locale.forLanguageTag("ka");
        kaMessages.put("app.title", messageHelper.getMessage("app.title", georgianLocale));
        kaMessages.put("app.slogan", messageHelper.getMessage("app.slogan", georgianLocale));
        kaMessages.put("header.contact", messageHelper.getMessage("header.contact", georgianLocale));
        result.put("georgianMessages", kaMessages);
        
        return result;
    }
}
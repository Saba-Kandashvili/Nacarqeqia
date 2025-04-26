package com.aughma.nacarqeqia;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MappingConfig implements WebMvcConfigurer{
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("index");
//
//        // Register redirects
//        registry.addRedirectViewController("/home", "/");
    }
}

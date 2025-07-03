package com.aughma.nacarqeqia.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImageResourceConfig implements WebMvcConfigurer {

    // Inject the path from application.properties
    @Value("${nacarqeqia.images.base-path}")
    private String imagesBasePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Make sure the path ends with a slash
        String location = imagesBasePath.endsWith("/") ? imagesBasePath : imagesBasePath + "/";

        registry.addResourceHandler("/image/**")
                .addResourceLocations("file:" + location);
    }
}
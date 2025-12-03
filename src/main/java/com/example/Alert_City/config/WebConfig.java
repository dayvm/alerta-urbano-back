package com.example.Alert_City.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Mapeia a URL "seu-site.com/uploads/..." para a pasta "uploads/" na raiz do servidor
        registry.addResourceHandler("/uploads/**")
        .addResourceLocations("file:./uploads/");
    }
}
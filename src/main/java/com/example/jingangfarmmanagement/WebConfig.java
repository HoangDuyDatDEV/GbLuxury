package com.example.jingangfarmmanagement;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Đảm bảo rằng thư mục uploads sẽ được phục vụ dưới URL /uploads/**
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("classpath:/uploads/");  // Thư mục uploads trong resources
    }
}

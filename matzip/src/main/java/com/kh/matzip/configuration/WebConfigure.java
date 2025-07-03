package com.kh.matzip.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull;

@Configuration
    public class WebConfigure implements WebMvcConfigurer {
        @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        String uploadPath = "file:" + System.getProperty("user.dir").replace("\\", "/") + "/uploads/";
        System.out.println(uploadPath);
        System.out.println(272727);
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath);
    }

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        // registry.addMapping("/**")
        //         .allowedOrigins("http://localhost:5173")
        //         .allowedMethods("*")
        //         .allowedHeaders("*")
        //         .allowCredentials(true);
    }
}
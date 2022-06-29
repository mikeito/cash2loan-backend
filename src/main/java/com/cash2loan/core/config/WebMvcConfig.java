package com.cash2loan.core.config;


import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowedOrigins("http://localhost:3000")
                .allowCredentials(true)
                .maxAge(-1);
    }

    @SneakyThrows
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        String uploadDirectory = "file:///C:/Users/mike%20ito/Documents/springboot%20projects/cash2loan/uploaded-images/";
//        String uploadDirectory = Paths.get("uploaded-images").toRealPath().toString();
        String uploadDirectory = Paths.get("uploaded-images").toUri().toString();
        registry
                .addResourceHandler("/**")
                .addResourceLocations(uploadDirectory);
    }
}


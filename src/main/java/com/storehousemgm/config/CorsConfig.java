package com.storehousemgm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig{
//public class CorsConfig implements WebMvcConfigurer {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOriginPatterns("http://localhost:8080",
//                        "https://ecommerce-shopping-app-bcsb.onrender.com")
//                .allowedOriginPatterns("*")
//                .allowCredentials(true)
//                .allowedHeaders("*")
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .maxAge(3600);
//    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8080",
                        "https://ecommerce-shopping-app-bcsb.onrender.com",
                                "https://www.ecommerce-shopping-app-bcsb.onrender.com")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }



}


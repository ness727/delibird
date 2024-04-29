package com.megamaker.loginservice.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Component
public class CustomCorsConfig implements CorsConfigurationSource {
    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        CorsConfiguration corsConfig = new CorsConfiguration();

        corsConfig.setAllowedOrigins(List.of("localhost:3000"));
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));

        return corsConfig;
    }
}

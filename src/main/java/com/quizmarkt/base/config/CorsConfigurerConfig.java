package com.quizmarkt.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author anercan
 */

@Configuration
public class CorsConfigurerConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .maxAge(3600)
                        .allowedOrigins("https://anercan.github.io/","http://localhost:3000/")
                        .exposedHeaders("Authorization")
                        .allowedMethods("HEAD", "GET", "PUT", "POST", "OPTIONS");
            }
        };
    }

}



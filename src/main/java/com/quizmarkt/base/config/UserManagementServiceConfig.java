package com.quizmarkt.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class UserManagementServiceConfig {

    @Bean(name = "userManagementRestTemplate")
    public RestTemplate prepareRestTemplateForUserManagementService() {
        return new RestTemplate();
    }
}
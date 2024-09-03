package com.quizmarkt.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.quesmarkt.quesmarktbase")
@SpringBootApplication
public class QuesmarktBaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuesmarktBaseApplication.class, args);
    }
}

package com.quizmarkt.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.quizmarkt.base")
@SpringBootApplication
public class QuizMarktBaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuizMarktBaseApplication.class, args);
    }
}

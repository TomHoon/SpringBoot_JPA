package com.tomhoon.springbootdeveloper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class springbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(springbootApplication.class, args);
    }
}

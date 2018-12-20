package com.deepocr.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class CardApplication {
    @Autowired
    public static void main(String[] args) {
        SpringApplication.run(CardApplication.class, args);
    }
}

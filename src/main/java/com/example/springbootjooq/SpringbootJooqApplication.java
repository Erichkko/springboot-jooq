package com.example.springbootjooq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringbootJooqApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJooqApplication.class, args);
    }

}

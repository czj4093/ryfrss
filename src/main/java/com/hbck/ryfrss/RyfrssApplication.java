package com.hbck.ryfrss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class RyfrssApplication {

    public static void main(String[] args) {
        SpringApplication.run(RyfrssApplication.class, args);
    }

}

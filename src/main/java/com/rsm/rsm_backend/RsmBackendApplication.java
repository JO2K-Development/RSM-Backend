package com.rsm.rsm_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
//@SpringBootApplication
public class RsmBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(RsmBackendApplication.class, args);
    }

}

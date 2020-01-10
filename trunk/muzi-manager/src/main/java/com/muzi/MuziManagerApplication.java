package com.muzi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MuziManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MuziManagerApplication.class, args);
    }

}

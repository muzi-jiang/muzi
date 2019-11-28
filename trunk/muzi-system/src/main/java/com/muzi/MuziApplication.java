package com.muzi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MuziApplication {

	public static void main(String[] args) {
		SpringApplication.run(MuziApplication.class, args);
	}
}

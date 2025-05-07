package com.example.flashsale_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FlashsaleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlashsaleServiceApplication.class, args);
	}

}

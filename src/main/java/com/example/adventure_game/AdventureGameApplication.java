package com.example.adventure_game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
@SpringBootApplication(scanBasePackages = "com.example")
public class AdventureGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdventureGameApplication.class, args);
	}

}

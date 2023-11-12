package com.fish.fishNet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.fish.fishNet.Repository")
public class FishNetApplication {

	public static void main(String[] args) {
		SpringApplication.run(FishNetApplication.class, args);
	}

}

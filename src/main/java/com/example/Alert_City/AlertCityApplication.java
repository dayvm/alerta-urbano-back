package com.example.Alert_City;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.example.Alert_City.model")
@EnableJpaRepositories(basePackages = "com.example.Alert_City.repository")
public class AlertCityApplication {
	public static void main(String[] args) {
		SpringApplication.run(AlertCityApplication.class, args);
	}
}

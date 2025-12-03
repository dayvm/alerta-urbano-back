package com.example.Alert_City;
import jakarta.annotation.PostConstruct; 
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import java.util.TimeZone;
@SpringBootApplication
@EntityScan(basePackages = "com.example.Alert_City.model")
@EnableJpaRepositories(basePackages = "com.example.Alert_City.repository")
public class AlertCityApplication {
	public static void main(String[] args) {
		SpringApplication.run(AlertCityApplication.class, args);
	}

	@PostConstruct
    public void init() {
        // Força a aplicação a usar o horário de Recife/Brasília (UTC-3)
        TimeZone.setDefault(TimeZone.getTimeZone("America/Recife"));
    }
}

package com.example.talky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TalkyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TalkyApplication.class, args);
	}

}

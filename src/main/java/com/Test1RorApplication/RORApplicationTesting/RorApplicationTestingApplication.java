package com.Test1RorApplication.RORApplicationTesting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RorApplicationTestingApplication {

	public static void main(String[] args) {
		SpringApplication.run(RorApplicationTestingApplication.class, args);
	}

}

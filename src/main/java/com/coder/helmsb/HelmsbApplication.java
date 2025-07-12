package com.coder.helmsb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class HelmsbApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelmsbApplication.class, args);
	}


	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome to the Spring Boot REST API! with help and docker kubernetes.";
	}

}

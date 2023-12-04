package com.pubo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.pubo"})
public class EladminSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EladminSystemApplication.class, args);
	}

}

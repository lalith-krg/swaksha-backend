package com.swaksha.consentmanagerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ConsentManagerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsentManagerServiceApplication.class, args);
	}

}

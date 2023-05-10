package com.swaksha.consentmanagerservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class ConsentManagerServiceApplication {

	public static void main(String[] args) {
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.registerModule(new JavaTimeModule());
//		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		SpringApplication.run(ConsentManagerServiceApplication.class, args);
	}

}

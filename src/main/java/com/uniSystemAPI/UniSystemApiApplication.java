package com.uniSystemAPI;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class UniSystemApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniSystemApiApplication.class, args);
	}

}

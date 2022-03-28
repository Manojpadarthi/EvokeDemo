package com.evoke.demo.employee;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;




@SpringBootApplication
@EnableCaching
public class EvokeDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvokeDemoApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		
		return new ModelMapper();
	}
	
	

	
	
}

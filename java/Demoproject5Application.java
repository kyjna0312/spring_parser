package com.example.demoproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

//@RestController
@SpringBootApplication
public class Demoproject5Application {
	
	/*
	@RequestMapping(value="/")
	public String hello() {
		return "HelloWorld";
	}*/

	public static void main(String[] args) {
		SpringApplication.run(Demoproject5Application.class, args);
	}
}

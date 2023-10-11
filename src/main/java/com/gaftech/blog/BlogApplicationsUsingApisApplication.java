package com.gaftech.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/*
	1. It is used to mark as configuration class 
	2. Combination of @Configuration,@EnableAutoConfiguration and @ComponentScan 
 */
@SpringBootApplication
public class BlogApplicationsUsingApisApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplicationsUsingApisApplication.class, args);
	}

	
}

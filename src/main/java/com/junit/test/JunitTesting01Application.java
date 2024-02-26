package com.junit.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.junit.test.service.EmployeeService;

@SpringBootApplication
public class JunitTesting01Application {


	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(JunitTesting01Application.class, args);

	}
	

}

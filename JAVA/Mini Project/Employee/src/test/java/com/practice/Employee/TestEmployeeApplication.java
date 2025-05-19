package com.practice.Employee;

import org.springframework.boot.SpringApplication;

public class TestEmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.from(EmployeeApplication::main).run(args);
	}

}
